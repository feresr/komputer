class RAM8 : RAM() {
    private val registers = arrayOf(Register(), Register(), Register(), Register(), Register(), Register(), Register(), Register())

    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        return registers[address.toInt()].invoke(input, load)
    }

    override fun toString(): String {
        return registers.map { it.toString() }.reduce { acc, s -> acc + s }
    }

    companion object {
        const val NUM_REGISTERS = 8
    }
}