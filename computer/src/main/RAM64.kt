class RAM64 : RAM() {

    private val registers = arrayOf(RAM8(),RAM8(),RAM8(),RAM8(),RAM8(),RAM8(),RAM8(),RAM8())

    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        return registers[address / RAM8.NUM_REGISTERS](input, load, (address % RAM8.NUM_REGISTERS).toShort())
    }

    override fun toString(): String {
        return registers.map { it.toString() }.reduce { acc, s -> acc + s }
    }

    companion object {
        const val NUM_CHIPS = RAM8.NUM_REGISTERS * 8
    }
}