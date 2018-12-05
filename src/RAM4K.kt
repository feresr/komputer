class RAM4K : (Short, Boolean, Short) -> Short {

    private val registers = arrayOf(RAM512(), RAM512(), RAM512(), RAM512(), RAM512(), RAM512(), RAM512(), RAM512())

    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        return registers[address / RAM512.NUM_CHIPS](input, load, address)
    }

    override fun toString(): String {
        return registers.map { it.toString() }.reduce { acc, s -> acc + s }
    }

    companion object {
        const val NUM_CHIPS = RAM512.NUM_CHIPS * 8
    }
}