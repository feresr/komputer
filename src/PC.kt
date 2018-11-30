import java.util.*

class PC(private val register: Register) : (BitSet, Boolean, Boolean, Boolean) -> BitSet {

    override fun invoke(input: BitSet, load: Boolean, increment: Boolean, reset: Boolean): BitSet {

        //reset: register(register(BitSet(), reset),
        //load : register(input, load)


        register(increment(register(BitSet(), reset)), and(not(reset), increment))


        return register(input, load)
    }
}