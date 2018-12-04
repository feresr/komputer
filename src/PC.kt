class PC(private val register: Register) : (Short, Boolean, Boolean, Boolean) -> Short {

    override fun invoke(input: Short, load: Boolean, increment: Boolean, reset: Boolean): Short {
        //reset: register(register(BitSet(), reset),
        //load : register(input, load)
        register(increment(register(0, reset)), and(not(reset), increment))
        return register(input, load)
    }
}