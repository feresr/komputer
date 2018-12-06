abstract class RAM : ROM {
    abstract operator fun invoke(input: Short, load: Boolean, address: Short): Short
    final override fun invoke(address: Short): Short {
        return invoke(0, false, address)
    }
}