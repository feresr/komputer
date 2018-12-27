import java.io.File

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        print("Please specify a .asm assembly file to assemble")
        return
    }

    val file = File(args[0])
    val writer = File("${file.parent}/${file.nameWithoutExtension}.cmp").writer()
    assembleFile(file).forEach { writer.appendln(it.toString()); }
    writer.flush()
}