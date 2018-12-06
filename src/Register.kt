class Register(private var state : Short = 0) : (Short, Boolean) -> Short, RAM {

    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        return invoke(input, load)
    }

    override fun invoke(input: Short, load: Boolean): Short {
        if (load) state = input
        return state
    }

    override fun toString(): String {
        return " $state "
    }
}