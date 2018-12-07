
class ROM32K(private val program : ShortArray) : (Short) -> Short {
    override fun invoke(address: Short): Short = program[address.toInt()]
}

// Original implementation: A ROM is nothing more than a read-only RAM memory chip. This was the original implementation.
// The above implementation makes it easier to load a program into the ROM
/*
class ROM32K : (Short) -> Short {

    private val ram = RAM32K()

    override fun invoke(address: Short): Short {
        return ram(0, false, address)
    }
}*/
