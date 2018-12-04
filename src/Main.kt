
fun main(args: Array<String>) {

    val program = ShortArray(10)

    //Add ram[0] to ram[1] and store it to ram[2]
    program[0] = 0
    program[1] = -1008
    program[2] = 1
    program[3] = -3952
    program[4] = 2
    program[5] = -7416

    Computer(rom = ROM32K(program), cpu = CPU(PC(Register()), ALU(), Register(), Register()), memory = RAM8(), reset = { false })

}