import java.io.File
import kotlin.streams.toList

fun main(args: Array<String>) {

    var computerOn = true
    val program = File(args[0])
            .bufferedReader()
            .lines().map<Short> { it.toShort() }.toList().toShortArray()

    val ram = VideoRam()
    val screen = Screen(ram.videoMemory, onWindowClosed = { computerOn = false })
    val computer = Computer(
            rom = Cartridge(program),
            cpu = CPU(PC(Register()), ALU(), registerA = Register(), registerD = Register()), memory = ram,
            reset = { false }
    )

    while (computerOn) {
        computer.step()
        screen.refresh()
    }
}