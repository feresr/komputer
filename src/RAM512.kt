class RAM512 : RAM {

    private val registers = arrayOf(RAM64(), RAM64(), RAM64(), RAM64(), RAM64(), RAM64(), RAM64(), RAM64())

    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        return registers[address / RAM64.NUM_CHIPS](input, load, (address % RAM64.NUM_CHIPS).toShort())
    }

    override fun toString(): String {
        return registers.map { it.toString() }.reduce { acc, s -> acc + s }
    }

    companion object {
        const val NUM_CHIPS = RAM64.NUM_CHIPS * 8
    }
}