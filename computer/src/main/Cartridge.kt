
class Cartridge(private val program : ShortArray) : ROM {
    override fun invoke(address: Short): Short = program[address.toInt()]
}

// Original implementation: A ROM is nothing more than a read-only RAM memory chip. This was the original implementation.
// The above implementation makes it easier to load a program into the ROM
/*
class CartdrigeROM : (Short) -> Short {

    private val ram = RAM32K()

    override fun invoke(address: Short): Short {
        return ram(0, false, address)
    }
}*/
