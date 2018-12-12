class CPU(private val pc: PC,
          private val alu: ALU,
          val registerA: Register,
          private val registerD: Register) : (Short, Short, Boolean) -> Unit {

    var currentPc : Short = 0
    var currentAddressM : Short = 0
    var outM : Short = 0
    var writeM = false

    // Registers: D A  M (ram[A])
    // A-Instruction: @value                    0value

    // C-Instruction: [dest] = comp ; [jmp]     1 u u a c1 c2 c3 c4 c5 c6 d1 d2 d3 j1 j2 j3
    // u = unused, a c = computation / control bit, d = destination, j = jump

    override fun invoke(inst: Short, inM: Short, reset: Boolean) {
        val instruction = inst.toBinaryReversed()
        val opCode = instruction[15]

        registerA(input = inst, load = not(opCode))
        //outputs
        val aMout = mux(registerA(), inM, and(opCode, instruction[12]))
        outM = alu(registerD(), aMout, zx = instruction[11], nx = instruction[10],
                zy = instruction[9], ny = instruction[8], f = instruction[7],
                no = instruction[6])


        currentAddressM = registerA()
        val loadA = and(opCode, instruction[5]) // the 10 bit of a C instruction tells us if we should save in A
        registerA(input = outM, load = loadA)

        val loadD = and(opCode, instruction[4]) // the 11 bit zof a C instruction tells us if we should save in D
        registerD(input = outM, load = loadD)

        writeM = and(opCode, instruction[3])

        val jlt = and(alu.ng, instruction[2])
        val jeq = and(alu.zr, instruction[1])

        val zeroOrNeg = or(alu.zr, alu.ng)
        val positive = not(zeroOrNeg)
        val jgt = and(positive, instruction[0])
        val jle = or(jeq, jlt)
        val jumpToA = or(jle, jgt)
        val pcLoad = and(opCode, jumpToA)
        val pcInc = not(pcLoad)

        currentPc = pc(registerA(), load = pcLoad, increment = pcInc, reset = reset)
    }

    override fun toString(): String {
        return "A:$registerA D:$registerD"
    }
}