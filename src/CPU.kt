import java.util.*


class CPU(private val pc: PC,
          private val alu: ALU,
          private val registerA: Register,
          private val registerD: Register) : (BitSet, BitSet, Boolean) -> Unit {

    override fun invoke(instruction: BitSet, inM: BitSet, reset: Boolean) {

        val Ainstruction = not(instruction[15])
        val Cinstruction = instruction[15]

        val aRegisterIn = mux16(instruction, alu.output, Ainstruction)

        val aluToA = and(Cinstruction, instruction[5])
        val loadA = or(Ainstruction, aluToA)
        val aRegisterOut = registerA(aRegisterIn, loadA)

        val aMout = mux16(aRegisterOut, inM, instruction[12])
        val loadD = and(Cinstruction, instruction[4])
        val dRegisterOut = registerD(alu.output, loadD)

        //outputs
        val outM = alu(dRegisterOut, aMout, zx = instruction[11], nx = instruction[10],
                zy = instruction[9], ny = instruction[8], f = instruction[7],
                no = instruction[6])



        val writeM = and(Cinstruction, instruction[3])

        val jeq = and(alu.zr, instruction[1])
        val jlt = and(alu.ng, instruction[2])
        val zeroOrNeg = or(alu.zr, alu.ng)
        val positive = not(zeroOrNeg)
        val jgt = and(positive, instruction[0])
        val jle = or(jeq, jlt)
        val jumpToA = or(jle, jgt)
        val pcLoad = and(Cinstruction, jumpToA)
        val pcInc = not(pcLoad)


        val PC = pc(aRegisterOut, pcInc, pcLoad, reset)
        val addressM = aRegisterOut

    }
}