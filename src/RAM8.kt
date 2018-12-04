class RAM8 : (Short, Boolean, Short) -> Short {
    private val registers = arrayOf(Register(1), Register(2), Register(), Register(), Register(), Register(0), Register(1), Register(1))

    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        return registers[address % registers.size].invoke(input, load)
    }

    override fun toString(): String {
        return registers.map { it.toString() }.reduce { acc, s -> acc + s }
    }
}