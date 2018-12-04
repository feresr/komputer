class RAM512 : (Short, Boolean, Short) -> Short {

    private val registers = arrayOf(RAM64(), RAM64(), RAM64(), RAM64(), RAM64(), RAM64(), RAM64(), RAM64())

    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        return registers[address % registers.size](input, load, address)
    }
}