import java.util.*

class ALU : (BitSet, BitSet, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean) -> BitSet {

    //output
    var zr: Boolean = false
    var ng: Boolean = false

    override fun invoke(x: BitSet,
                        y: BitSet,
                        zx: Boolean,
                        nx: Boolean,
                        zy: Boolean,
                        ny: Boolean,
                        f: Boolean,
                        no: Boolean
    ): BitSet {
        val zrx = mux16(x, BitSet(), zx)
        val ngx = mux16(zrx, not16(zrx), nx)
        val zry = mux16(y, BitSet(), zy)
        val ngy = mux16(zry, not16(zry), ny)
        val fxy = mux16(add(ngx, ngy), and16(ngx, ngy), f)
        val result = mux16(fxy, not16(fxy), no)

        zr = zero(result)
        ng = negative(result)

        output = result
        return result
    }


    var output: BitSet = BitSet()
}