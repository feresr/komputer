import java.util.*

/**
 * The nand gate is the only 'given' gate. All other gates cannot use built-in logic operations and
 * must instead be built using existing gates.
 */
fun nand(x: Boolean, y: Boolean): Boolean = !(x && y)

/**
 * Logic gates
 */
fun not(x: Boolean) = nand(x, x)
fun and(x: Boolean, y: Boolean) = not(nand(x, y))
fun or(x: Boolean, y: Boolean) = nand(not(x), not(y))
fun xor(x: Boolean, y: Boolean) = and(or(x, y), not(and(x, y)))
fun mux(x: Boolean, y: Boolean, sel: Boolean) = or(and(sel, y), and(not(sel), x))
fun dmux(x: Boolean, sel: Boolean): Pair<Boolean, Boolean> = Pair(and(not(sel), x), and(sel, x))

/**
 * multi-bit variants
 */
const val NBIT = 16

fun BitSet.forEach(f: (Int) -> Boolean): BitSet = this.also {
    (0 until this.size()).forEach { this[it] = f(it) }
}

fun not16(x: BitSet): BitSet = x.also { it.flip(0, NBIT) }
fun and16(x: BitSet, y: BitSet): BitSet = x.forEach { and(x[it], y[it]) }
fun or16(x: BitSet, y: BitSet): BitSet = BitSet(NBIT).forEach { or(x[it], y[it]) }
fun xor16(x: BitSet, y: BitSet): BitSet = BitSet(NBIT).forEach { xor(x[it], y[it]) }
fun mux16(x: BitSet, y: BitSet, sel: Boolean) = BitSet(NBIT).forEach { mux(x[it], y[it], sel) }

/**
 * Add Operations
 */

//half hadder
fun add(x: Boolean, y: Boolean): Pair<Boolean, Boolean> = Pair(and(x, y), xor(x, y))

//full added
fun add(x: Boolean, y: Boolean, z: Boolean): Pair<Boolean, Boolean> {
    val (a, b) = add(x, y)
    val (c, d) = add(b, z)
    return Pair(or(c, a), d)
}

/**
 * Multi-bit variants
 */
fun add(x: BitSet, y: BitSet): BitSet {
    val result = BitSet(NBIT)
    val (carry, number) = add(x[NBIT - 1], y[NBIT - 1])
    result[NBIT - 1] = number

    var c = carry
    (NBIT - 2 downTo 0).forEach {
        val (cr, n) = add(x[it], y[it], c)
        c = cr

        result[it] = n
    }

    return result
}

fun increment(x: BitSet): BitSet {
    return add(x, BitSet(NBIT).also { it[NBIT - 1] = true })
}

fun substract(x: BitSet, y: BitSet): BitSet {
    val not = not16(y)
    return increment(add(x, not))
}

fun zero(x: BitSet): Boolean = x.cardinality() == 0
fun negative(x: BitSet): Boolean = x[0]