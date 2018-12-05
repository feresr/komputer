
interface RAM : (Short, Boolean, Short) -> Short {
    override fun invoke(input: Short, load: Boolean, address: Short): Short
}