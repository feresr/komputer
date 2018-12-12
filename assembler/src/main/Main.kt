import java.io.File



fun main(args: Array<String>) {
    if (args.isEmpty()) {
        print("Please specify the filename to assemble")
        return
    }
    val writer = File(args[0] + ".cmp").writer()
    assembleFile(File(args[0])).forEach { writer.appendln(it.toString()); }
    writer.flush()
}