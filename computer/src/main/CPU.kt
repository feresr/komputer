
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
        val opCode = inst[15]

        registerA(input = inst, load = not(opCode))
        //outputs
        val aMout = mux(registerA(), inM, and(opCode, inst[12]))
        outM = alu(registerD(), aMout, zx = inst[11], nx = inst[10],
                zy = inst[9], ny = inst[8], f = inst[7],
                no = inst[6])


        currentAddressM = registerA()
        val loadA = and(opCode, inst[5]) // the 10 bit of a C instruction tells us if we should save in A
        registerA(input = outM, load = loadA)

        val loadD = and(opCode, inst[4]) // the 11 bit zof a C instruction tells us if we should save in D
        registerD(input = outM, load = loadD)

        writeM = and(opCode, inst[3])

        val jlt = and(alu.ng, inst[2])
        val jeq = and(alu.zr, inst[1])

        val zeroOrNeg = or(alu.zr, alu.ng)
        val positive = not(zeroOrNeg)
        val jgt = and(positive, inst[0])
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