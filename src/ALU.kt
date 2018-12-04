
class ALU : (Short, Short, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean) -> Short {

    //output
    var zr: Boolean = false
    var ng: Boolean = false

    override fun invoke(x: Short,
                        y: Short,
                        zx: Boolean,
                        nx: Boolean,
                        zy: Boolean,
                        ny: Boolean,
                        f: Boolean,
                        no: Boolean
    ): Short {
        val zrx = mux(x, 0, zx)
        val ngx = mux(zrx, not(zrx), nx)
        val zry = mux(y, 0, zy)
        val ngy = mux(zry, not(zry), ny)
        val fxy = mux(and(ngx, ngy), add(ngx, ngy), f)
        val result = mux(fxy, not(fxy), no)

        zr = zero(result)
        ng = negative(result)

        output = result
        return result
    }

    var output: Short = 0
}