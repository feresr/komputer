class RAM8 : RAM() {

    private val registers = Array(8) { Register() }

    override fun invoke(
            input: Short,
            load: Boolean,
            address: Short): Short {
        return registers[address.toInt()].invoke(input, load)
    }

        override fun toString(): String {
        return registers.map { it.toString() }.reduce { acc, s -> acc + s }
    }

    companion object {
        const val NUM_REGISTERS = 8 * Register.NUM_REGISTERS
    }
}