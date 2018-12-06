class RAM32K : RAM {

    private val registers = arrayOf(RAM16K(), RAM16K())

    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        return registers[address / RAM16K.NUM_CHIPS](input, load, (address % RAM16K.NUM_CHIPS).toShort())
    }

    override fun toString(): String {
        return registers.map { it.toString() }.reduce { acc, s -> acc + s }
    }

    companion object {
        const val NUM_CHIPS = RAM16K.NUM_CHIPS * 2
    }
}