class RAM32K : (Short, Boolean, Short) -> Short {

    private val registers = arrayOf(RAM16K(), RAM16K())

    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        return registers[address % registers.size](input, load, address)
    }

    override fun toString(): String {
        return registers.map { it.toString() }.reduce { acc, s -> acc + s }
    }
}