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
        val Ainstruction = not(instruction[15])
        val Cinstruction = instruction[15]

        val aRegisterIn = mux(inst, alu.output, Ainstruction)

        val aluToA = and(Cinstruction, instruction[5])
        val loadA = or(Ainstruction, aluToA)
        val aRegisterOut = registerA(aRegisterIn, loadA)

        val aMout = mux(aRegisterOut, inM, instruction[12])
        val loadD = and(Cinstruction, instruction[4])
        val dRegisterOut = registerD(alu.output, loadD)

        //outputs
        outM = alu(dRegisterOut, aMout, zx = instruction[11], nx = instruction[10],
                zy = instruction[9], ny = instruction[8], f = instruction[7],
                no = instruction[6])

        writeM = and(Cinstruction, instruction[3])

        val jeq = and(alu.zr, instruction[1])
        val jlt = and(alu.ng, instruction[2])
        val zeroOrNeg = or(alu.zr, alu.ng)
        val positive = not(zeroOrNeg)
        val jgt = and(positive, instruction[0])
        val jle = or(jeq, jlt)
        val jumpToA = or(jle, jgt)
        val pcLoad = and(Cinstruction, jumpToA)
        val pcInc = not(pcLoad)

        currentPc = pc(aRegisterOut, pcInc, pcLoad, reset)
        currentAddressM = aRegisterOut

    }
}