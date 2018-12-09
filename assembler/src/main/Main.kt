import java.io.File

const val A_INSTRUCTION_PREFIX = "@"
const val COMMENT_START = "//"

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        print("Please specify the filename to assemble")
        return
    }
    val whiteSpaces = Regex("\\s")
    val writer = File(args[0] + ".cmp").writer()

    File(args[0])
            .bufferedReader()
            .lines()
            .map { it.replace(whiteSpaces, "") }
            .map { it -> it.split(COMMENT_START).firstOrNull().takeUnless { it.isNullOrBlank() } }
            .filter { it != null }
            .map { instruction ->
                if (instruction!!.startsWith(A_INSTRUCTION_PREFIX)) {
                    //A Instruction
                    "0" + String.format("%1$" + 15 + "s", Integer.toBinaryString(instruction.removePrefix(A_INSTRUCTION_PREFIX).toInt())).replace(' ', '0')
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
            .map { it.map { c -> c == '1' }.toBooleanArray().toShort() }
            .forEach { writer.appendln(it.toString()) }

    writer.flush()
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