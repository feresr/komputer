import java.io.File
import java.util.stream.Stream

private val symbols: MutableMap<String, String> = mutableMapOf(
        "R0" to "0000000000000000",
        "R1" to "0000000000000001",
        "R2" to "0000000000000010",
        "R3" to "0000000000000011",
        "R4" to "0000000000000100",
        "R5" to "0000000000000101",
        "R6" to "0000000000000110",
        "R7" to "0000000000000111",
        "R8" to "0000000000001000",
        "R9" to "0000000000001001",
        "R10" to "0000000000001010",
        "R11" to "0000000000001011",
        "R12" to "0000000000001100",
        "R13" to "0000000000001101",
        "R14" to "0000000000001110",
        "R15" to "0000000000001111",
        "SCREEN" to "0100000000000000",
        "KBD" to "0110000000000000",
        "SP" to "0000000000000000",
        "LCL" to "0000000000000001",
        "ARG" to "0000000000000010",
        "THIS" to "0000000000000011",
        "THAT" to "0000000000000100"
)


private const val A_INSTRUCTION_PREFIX = "@"
private const val COMMENT_START = "//"

fun assembleFile(file : File) : Stream<Short> {
    val whiteSpaces = Regex("\\s")

    var lineNumber = 0
    var varAddress = 16

    file.bufferedReader()
            .lines()
            .map { it.replace(whiteSpaces, "") }
            .map { it -> it.split(COMMENT_START).firstOrNull().takeUnless { it.isNullOrBlank() } }
            .filter { it != null }
            .forEach {
                if (it!!.startsWith("(")) {
                    symbols[it.drop(1).dropLast(1)] = String.format("%1$" + 16 + "s", Integer.toBinaryString(lineNumber)).replace(' ', '0')
                } else {
                    lineNumber++
                }
            }

    return file
            .bufferedReader()
            .lines()
            .map { it.replace(whiteSpaces, "") }
            .map { it -> it.split(COMMENT_START).firstOrNull().takeUnless { it.isNullOrBlank() } }
            .filter { it != null }
            .filter { it!!.startsWith("(").not() }
            .map { instruction ->
                if (instruction!!.startsWith(A_INSTRUCTION_PREFIX)) {
                    //A Instruction
                    val value = instruction.removePrefix(A_INSTRUCTION_PREFIX)
                    value.toIntOrNull()?.let {
                        String.format("%1$" + 16 + "s", Integer.toBinaryString(it)).replace(' ', '0')
                    }
                            ?:  symbols.getOrPut(value) {
                                String.format("%1$" + 16 + "s", Integer.toBinaryString(varAddress)).replace(' ', '0').also { varAddress++ }
                            }

                } else {
                    //C Instruction
                    val parsed = instruction
                            .let { if (it.contains("=")) it else "=$it" }
                            .let { if (it.contains(";")) it else "$it;" }

                    val dest = parsed.split("=").firstOrNull().takeUnless { it.isNullOrBlank() }
                    val comp = parsed.split("=").getOrNull(1).takeUnless { it.isNullOrBlank() }?.split(";")?.firstOrNull().takeUnless { it.isNullOrBlank() }
                    val jmp = parsed.split(";").getOrNull(1).takeUnless { it.isNullOrBlank() }

                    "111" + mapComp(comp) + mapDest(dest) + mapJmp(jmp)
                }
            }
            .map {
                it
                        .map { c -> c == '1' }
                        .toBooleanArray()
                        .toShort()
            }
}


private fun BooleanArray.toShort(): Short {
    var c = 1
    val firstBits = (Short.SIZE_BITS - 1 downTo 1).fold(0) { acc, cur ->
        acc + c * (if (this[cur]) 1 else 0).also { c *= 2 }
    }

    return (if (this[0]) firstBits + Short.MIN_VALUE else firstBits).toShort()
}

private fun mapDest(d: String?): String {
    return when (d) {
        null -> "000"
        "M" -> "001"
        "D" -> "010"
        "MD", "DM" -> "011"
        "A" -> "100"
        "AM", "MA" -> "101"
        "AD", "DA" -> "110"
        "AMD", "MAD", "DAM", "DMA", "MDA", "ADM" -> "111"
        else -> throw IllegalStateException("unknown dest command $d")
    }
}

private fun mapJmp(j: String?): String {
    return when (j) {
        null -> "000"
        "JGT" -> "001"
        "JEQ" -> "010"
        "JGE" -> "011"
        "JLT" -> "100"
        "JNE" -> "101"
        "JLE" -> "110"
        "JMP" -> "111"
        else -> throw IllegalStateException("unknown jump command $j")
    }
}

private fun mapComp(d: String?): String {
    return when (d) {
        "0" -> "0101010"
        "1" -> "0111111"
        "-1" -> "0111010"
        "D" -> "0001100"
        "A" -> "0110000"
        "M" -> "1110000"
        "!D" -> "0001101"
        "!A" -> "0110001"
        "!M" -> "1110001"
        "D+1" -> "0011111"
        "A+1" -> "0110111"
        "M+1" -> "1110111"
        "D-1" -> "0001110"
        "A-1" -> "0110010"
        "M-1" -> "1110010"
        "D+A", "A+D" -> "0000010"
        "D+M", "M+D" -> "1000010"
        "D-A" -> "0010011"
        "D-M" -> "1010011"
        "A-D" -> "0000111"
        "M-D" -> "1000111"
        "D&A", "A&D" -> "0000000"
        "D&M", "M&D" -> "1000000"
        "A|D", "D|A" -> "0010101"
        "M|D", "D|M" -> "1010101"
        else -> throw IllegalStateException("unknown comp command $d")
    }
}