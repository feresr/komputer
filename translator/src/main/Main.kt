import java.io.File

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        print("Please specify a .vm file to translate")
        return
    }

    val file = File(args[0])
    val writer = File("${file.parent}/${file.nameWithoutExtension}.asm").writer()
    translateFile(File(args[0])).forEach { writer.appendln(it.toString()); }
    writer.flush()
}