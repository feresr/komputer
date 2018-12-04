class RAM4K : (Short, Boolean, Short) -> Short {

    private val registers = arrayOf(RAM512(), RAM512(), RAM512(), RAM512(), RAM512(), RAM512(), RAM512(), RAM512())

    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        return registers[address % registers.size](input, load, address)
    }
}