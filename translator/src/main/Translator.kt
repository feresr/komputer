import java.io.File
import java.util.stream.Stream

private const val COMMENT_START = "//"

enum class CommandType { ADD, SUB, NEG, AND, NOT, EQ, LT, GT, OR, PUSH, POP, LABEL, GOTO, IF, FUNCTION, RETURN, CALL }
enum class Segment { LCL, ARG, THIS, THAT, CONSTANT, STATIC, POINTER, TEMP }

private fun commandType(command: String): CommandType {
    return when (command.toLowerCase()) {
        "push" -> CommandType.PUSH
        "pop" -> CommandType.POP
        "add" -> CommandType.ADD
        "sub" -> CommandType.SUB
        "neg" -> CommandType.NEG
        "eq" -> CommandType.EQ
        "lt" -> CommandType.LT
        "gt" -> CommandType.GT
        "or" -> CommandType.OR
        "and" -> CommandType.AND
        "not" -> CommandType.NOT
        else -> throw IllegalStateException("Unknown command type $command")
    }
}

private fun String.segment(): Segment {
    return when (this.toLowerCase()) {
        "local" -> Segment.LCL
        "argument" -> Segment.ARG
        "this" -> Segment.THIS
        "that" -> Segment.THAT
        "constant" -> Segment.CONSTANT
        "static" -> Segment.STATIC
        "pointer" -> Segment.POINTER
        "temp" -> Segment.TEMP
        else -> throw IllegalStateException("Unknown segment $this")
    }
}

private fun push(segment: Segment, value: Short): Stream<String> {

    return when (segment) {
        Segment.CONSTANT -> {
            listOf(
                    "// push $segment $value",
                    "@$value",
                    "D=A",
                    "@SP",
                    "A=M",
                    "M=D",
                    "@SP", //SP ++
                    "M=M+1"
            )
        }
        Segment.POINTER -> {
            listOf(
                    "// push $segment $value",
                    "@${ if (value == 0.toShort()) Segment.THIS else Segment.THAT }",
                    "D=M",//A = segment.address + value
                    "@SP",
                    "A=M",
                    "M=D",
                    "@SP", //SP ++
                    "M=M+1"
            )
        }
        Segment.TEMP -> {
            listOf(
                    "// push $segment $value",
                    "@5",
                    "D=A",
                    "@$value",
                    "A=D+A",
                    "D=M",      //A = segment.address + value
                    "@SP",
                    "A=M",
                    "M=D",
                    "@SP", //SP ++
                    "M=M+1"
            )
        }
        Segment.STATIC -> {
            listOf(
                    "// push $segment $value",
                    "@$fileName.$value",
                    "D=M",
                    "@SP",
                    "A=M",
                    "M=D",
                    "@SP", //SP ++
                    "M=M+1"
            )
        }
        else -> {
            listOf(
                    "// push $segment $value",
                    "@$segment",
                    "D=M",
                    "@$value",
                    "A=D+A",
                    "D=M",      //A = segment.address + value
                    "@SP",
                    "A=M",
                    "M=D",
                    "@SP", //SP ++
                    "M=M+1"
            )
        }
    }.stream()

}

private fun pop(segment: Segment, value: Short): Stream<String> {
    return when (segment) {
        Segment.CONSTANT -> throw IllegalStateException("pop from stack to $segment is not allowed")
        Segment.TEMP -> {
            listOf(
                    "// pop $segment $value",
                    "@5",
                    "D=A",
                    "@$value",
                    "D=D+A",    // D is now segment.address + value
                    "@R13",
                    "M=D",
                    "@SP",
                    "AM=M-1",
                    "D=M",    // SP = SP-1
                    "@R13",
                    "A=M",
                    "M=D"
            )
        }
        Segment.POINTER -> {
            listOf(
                    "// pop $segment $value",
                    "@${ if (value == 0.toShort()) Segment.THIS else Segment.THAT }",
                    "D=A",
                    "@R13",
                    "M=D",
                    "@SP",
                    "AM=M-1",
                    "D=M",    // SP = SP-1
                    "@R13",
                    "A=M",
                    "M=D"
            )
        }
        Segment.STATIC -> {
            // "@$fileName.$value"
            listOf(
                    "// pop $segment $value",
                    "@SP",
                    "AM=M-1",
                    "D=M",    // SP = SP-1
                    "@$fileName.$value",
                    "M=D"
            )
        }
        else -> {
            listOf(
                    "// pop $segment $value",
                    "@$segment",
                    "D=M",
                    "@$value",
                    "D=D+A",    // D is now segment.address + value
                    "@R13",
                    "M=D",
                    "@SP",
                    "AM=M-1",
                    "D=M",    // SP = SP-1
                    "@R13",
                    "A=M",
                    "M=D"
            )
        }
    }.stream()
}

private fun add(): Stream<String> {
    return listOf(
            "// add",
            "@SP",
            "AM=M-1",
            "D=M",
            "A=A-1",
            "M=M+D"
    ).stream()
}

private fun sub(): Stream<String> {
    return listOf(
            "// sub",
            "@SP",
            "AM=M-1",
            "D=M",
            "A=A-1",
            "M=M-D"
    ).stream()
}

var fileName : String = ""

fun translateFile(file: File): Stream<String> {

    fileName = file.nameWithoutExtension

    return file.bufferedReader()
            .lines()
            .map { it.trim() }
            .map { it -> it.split(COMMENT_START).firstOrNull().takeUnless { it.isNullOrBlank() } }
            .filter { it != null }
            .flatMap {
                val command = it!!.split(" ")
                when (commandType(command[0])) {
                    CommandType.PUSH -> push(command[1].segment(), command[2].toShort())
                    CommandType.POP -> pop(command[1].segment(), command[2].toShort())
                    CommandType.ADD -> add()
                    CommandType.SUB -> sub()
                    CommandType.NEG -> neg()
                    CommandType.EQ -> eq()
                    CommandType.GT -> gt()
                    CommandType.LT -> sub()
                    CommandType.AND -> sub()
                    CommandType.OR -> sub()
                    CommandType.NOT -> sub()
                    CommandType.LABEL -> pop(command[1].segment(), command[2].toShort())
                    CommandType.GOTO -> pop(command[1].segment(), command[2].toShort())
                    CommandType.IF -> pop(command[1].segment(), command[2].toShort())
                    CommandType.FUNCTION -> pop(command[1].segment(), command[2].toShort())
                    CommandType.RETURN -> pop(command[1].segment(), command[2].toShort())
                    CommandType.CALL -> pop(command[1].segment(), command[2].toShort())
                }
            }
}

private var jumpFlag = 0

fun eq(): Stream<String> {
    jumpFlag++
    return listOf(
            "// eq",
            "@SP",
            "AM=M-1",
            "D=M",
            "A=A-1",
            "D=M-D",
            "@TRUE.$jumpFlag",
            "D;JEQ",
            "@SP",
            "A=M-1",
            "M=0",
            "@END.$jumpFlag",
            "0;JMP",
            "(TRUE.$jumpFlag)",
            "@SP",
            "A=M-1",
            "M=-1",
            "(END.$jumpFlag)"
    ).stream()
}

fun neg(): Stream<String> {
    TODO()
}

fun gt(): Stream<String> {
    TODO()
}
