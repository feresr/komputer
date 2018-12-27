import java.io.File
import java.util.stream.Stream

private const val COMMENT_START = "//"

enum class CommandType { ARITHMETIC, PUSH, POP, LABEL, GOTO, IF, FUNCTION, RETURN, CALL }

private fun commandType() : CommandType {
    TODO("Not implemented")
}


private fun writeArithmetic() {}
private fun writePushPop() {}

fun translateFile(file: File): Stream<Short> {
    val whiteSpaces = Regex("\\s")

    var lineNumber = 0
    var varAddress = 16
    file.bufferedReader()
            .lines()
            .map { it.replace(whiteSpaces, "") }
            .map { it -> it.split(COMMENT_START).firstOrNull().takeUnless { it.isNullOrBlank() } }
            .filter { it != null }

    TODO("Not implemented")
}