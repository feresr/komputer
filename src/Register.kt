class Register(var state : Short = 0) : (Short, Boolean) -> Short {

    override fun invoke(input: Short, load: Boolean): Short {
        if (load) state = input
        return state
    }

    override fun toString(): String {
        return " $state "
    }
}