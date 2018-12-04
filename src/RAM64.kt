class RAM64 : (Short, Boolean, Short) -> Short {

    private val registers = arrayOf(RAM8(),RAM8(),RAM8(),RAM8(),RAM8(),RAM8(),RAM8(),RAM8())

    override fun invoke(input: Short, load: Boolean, address: Short): Short {
        return registers[address % registers.size](input, load, address)
    }

    override fun toString(): String {
        return registers.map { it.toString() }.reduce { acc, s -> acc + s }
    }
}