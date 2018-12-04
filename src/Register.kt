class Register : (Short, Boolean) -> Short {

    private var state: Short = 0

    override fun invoke(input: Short, load: Boolean): Short {
        if (load) state = input
        return state
    }
}