class RAM8 : (Short, Boolean, Short) -> Short {
    private val registers = arrayOf(Register(), Register(), Register(), Register(), Register(), Register(), Register(), Register())

    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        return registers[address % registers.size].invoke(input, load)
    }
}