class VideoRam : RAM() {

    val videoMemory = RAM8K()
    val keyboardMemory = Register()

    private val registers = arrayOf(RAM8K(), RAM8K(), videoMemory, keyboardMemory)

    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        return registers[address / RAM8K.NUM_CHIPS](input, load, (address % RAM8K.NUM_CHIPS).toShort())
    }

    override fun toString(): String {
        return registers.map { it.toString() }.reduce { acc, s -> acc + s }
    }

    companion object {
        const val NUM_CHIPS = RAM8K.NUM_CHIPS * 3 + Register.NUM_REGISTERS
    }
}