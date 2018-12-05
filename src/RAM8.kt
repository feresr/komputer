class RAM8 : RAM {
    private val registers = arrayOf(Register(1), Register(1), Register(), Register(), Register(), Register(0), Register(0), Register(0))

    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        return registers[address % registers.size].invoke(input, load)
    }

    override fun toString(): String {
        return registers.map { it.toString() }.reduce { acc, s -> acc + s }
    }

    companion object {
        const val NUM_REGISTERS = 8
    }
}