class CPU(private val pc: PC,
          private val alu: ALU,
          private val registerA: Register,
          private val registerD: Register) : (Short, Short, Boolean) -> Unit {

    var currentPc : Short = 0
    var currentAddressM : Short = 0
    var outM : Short = 0
    var writeM = false

    override fun invoke(inst: Short, inM: Short, reset: Boolean) {
        val instruction = inst.toBinary()
        val opCode = instruction[0]
        val aRegisterIn = mux(inst, alu.output, opCode)

        val aluToA = and(opCode, instruction[10]) // the 10 bit of a C instruction tells us if we should save in A
        val loadA = or(not(opCode), aluToA)
        val aRegisterOut = registerA(input = aRegisterIn, load = loadA)

        //outputs
        val aMout = mux(aRegisterOut, inM, and(opCode, instruction[3]))
        outM = alu(registerD(input = 0, load = false), aMout, zx = instruction[4], nx = instruction[5],
                zy = instruction[6], ny = instruction[7], f = instruction[8],
                no = instruction[9])

        val loadD = and(opCode, instruction[11]) // the 11 bit of a C instruction tells us if we should save in D
        registerD(input = outM, load = loadD)

        writeM = and(opCode, instruction[12])

        val jeq = and(alu.zr, instruction[14])
        val jlt = and(alu.ng, instruction[13])

        val zeroOrNeg = or(alu.zr, alu.ng)
        val positive = not(zeroOrNeg)
        val jgt = and(positive, instruction[15])
        val jle = or(jeq, jlt)
        val jumpToA = or(jle, jgt)
        val pcLoad = and(opCode, jumpToA)
        val pcInc = not(pcLoad)

        currentPc = pc(aRegisterOut, load = pcLoad, increment = pcInc, reset = reset)
        currentAddressM = aRegisterOut

    }

    override fun toString(): String {
        return "A:$registerA D:$registerD"
    }
}