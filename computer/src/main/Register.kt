class Register(private var state: Short = 0) : RAM() {

    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        return invoke(input, load)
    }

    operator fun invoke(input: Short, load: Boolean): Short {
        if (load) state = input
        return state
    }

    operator fun invoke(): Short {
        return state
    }

    override fun toString(): String = " $state "

    companion object {
        const val NUM_REGISTERS = 1
    }
}