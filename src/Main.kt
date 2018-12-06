
fun main(args: Array<String>) {

    val program = ShortArray(10)

    //Add ram[0] to ram[1] and store it to ram[2]

    // Registers: D A  M (ram[A])
    // A-Instruction: @value                    0value

    // C-Instruction: [dest] = comp ; [jmp]     1 u u a c1 c2 c3 c4 c5 c6 d1 d2 d3 j1 j2 j3
    // u = unused, a c = computation / control bit, d = destination, j = jump

    program[0] = 0      // 0000000000000000     @0      Sets A to 0, makes M point to RAM[0]
    program[1] = -1008  // 1111110000010000     D=M     Makes D equal to RAM[0]
    program[2] = 1      // 0000000000000001     @1      Sets A to 1, makes M point to RAM[1]
    program[3] = -3952  // 1111000010010000     D=D+M   Makes D equal to RAM[0](previous value) plus RAM[1](M)
    program[4] = 2      // 0000000000000010     @2      Sets A to 2, makes M point to RAM[2]
    program[5] = -7416  // 1110001100001000     M=D     Stores D into RAM[2]
    program[6] = 2      // 0000000000000010     @2      (redundant) Sets A to 2, makes M point to RAM[2]
    program[7] = -4089  // 1111000000000111     JMP     Unconditional jump

    val videoRam = RAM8K()
    val screen = Screen(videoRam)
    Computer(
            rom = ROM32K(program),
            cpu = CPU(PC(Register()), ALU(), registerA = Register(), registerD = Register()), memory = videoRam,
            reset = { false },
            clock = { screen.repaint() }
    )
}