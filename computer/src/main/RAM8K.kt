class RAM8K : RAM() {

    private val registers = arrayOf(RAM4K(), RAM4K())

    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        val register = registers[address / RAM4K.NUM_CHIPS]
        return register(input, load, (address % RAM4K.NUM_CHIPS).toShort())
    }

    override fun toString(): String {
        return registers.map { it.toString() }.reduce { acc, s -> acc + s }
    }

    companion object {
        const val NUM_CHIPS = RAM4K.NUM_CHIPS * 2
    }
}