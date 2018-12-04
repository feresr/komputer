class RAM16K : (Short, Boolean, Short) -> Short {

    private val registers = arrayOf(RAM4K(), RAM4K(), RAM4K(), RAM4K(), RAM4K(), RAM4K(), RAM4K(), RAM4K())

    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        return registers[address % registers.size](input, load, address)
    }
}