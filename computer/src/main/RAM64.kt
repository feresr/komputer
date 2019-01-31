
class RAM64 : RAM() {

    private val internalRAM = Array(8) { RAM8() }

    override fun invoke(input: Short,
                        load: Boolean,
                        address: Short): Short {
        val internalRam = internalRAM[address / RAM8.NUM_REGISTERS]
        return internalRam(input, load, (address % RAM8.NUM_REGISTERS).toShort())
    }

    override fun toString(): String {
        return internalRAM.map { it.toString() }.reduce { acc, s -> acc + s }
    }

    companion object {
        const val NUM_CHIPS = RAM8.NUM_REGISTERS * 8
    }
}