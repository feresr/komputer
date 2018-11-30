import java.util.*

class Register : (BitSet, Boolean) -> BitSet {

    private var state: BitSet = BitSet(16)

    override fun invoke(input: BitSet, load: Boolean): BitSet {
        and16()
        if (load) state = input
        return state
    }
}