import java.io.File

fun main(args: Array<String>) {

    var computerOn = true
    val program = ShortArray(40000)
    var i = 0
    File(args[0])
            .bufferedReader()
            .lines()
            .forEach { program[i] = it.toShort(); i++; }

    val ram = VideoRam()
    val screen = Screen(ram.videoMemory, onWindowClosed = { computerOn = false })
    Computer(
            rom = Cartridge(program),
            cpu = CPU(PC(Register()), ALU(), registerA = Register(), registerD = Register()), memory = ram,
            reset = { false },
            clock = { screen.refresh() },
            computerOn = { computerOn }
    )
}