
fun main(args: Array<String>) {

    val program = ShortArray(10)

    //Add ram[0] to ram[1] and store it to ram[2]
    // A-Instruction: @value
    // C-Instruction: [dest] = comp ; [jmp]

    program[0] = 0      // 0000000000000000     @0
    program[1] = -1008  // 1111110000010000     D=M
    program[2] = 1      // 0000000000000001     @1
    program[3] = -3952  // 1111000010010000     D=D+M
    program[4] = 2      // 0000000000000010     @2
    program[5] = -7416  // 1110001100001000     M=D

    Computer(rom = ROM32K(program), cpu = CPU(PC(Register()), ALU(), Register(), Register()), memory = RAM8(), reset = { false })

}