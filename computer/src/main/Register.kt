/**
 * Holds a single 16 bit number
 */
class Register(private var state: Short = 0) : RAM() {

    operator fun invoke(input: Short, load: Boolean): Short {
        if (load) state = input
        return state
    }

    operator fun invoke(): Short {
        return state
    }

    companion object {
        const val NUM_REGISTERS = 1
    }


    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        return invoke(input, load)
    }

    override fun toString(): String = " $state "
}