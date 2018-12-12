import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.math.abs

/**
 * The nand gate is the only 'given' gate. All other gates cannot use built-in logic operations and
 * must instead rely on existing gates.
 */
fun nand(x: Boolean, y: Boolean): Boolean = !(x && y)

fun nand(x: Short, y: Short): Short = (x and y).inv()

/**
 * Utilities
 */
fun Boolean.toShort(): Short = if (this) -1 else 0

fun BooleanArray.toShort(): Short {
    var c = 1
    val firstBits = (Short.SIZE_BITS - 1 downTo 1).fold(0) { acc, cur ->
        acc + c * (if (this[cur]) 1 else 0).also { c *= 2 }
    }

    return (if (this[0]) firstBits - Short.MIN_VALUE else firstBits).toShort()
}


fun Short.toBinary(): BooleanArray = this.toBinaryReversed().reversedArray()

fun Short.toBinaryReversed(): BooleanArray {
    var current = if (this >= 0) this else (this + 1).toShort()
    val positive = BooleanArray(Short.SIZE_BITS) {
        (abs(current % 2) == 1).also { current = current.div(2).toShort() }
    }

    return if (this >= 0) {
        positive
    } else {
        positive.map { !it }.toBooleanArray()
    }
}

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
 * 16-bit variants
 */
fun not(x: Short): Short = nand(x, x)

fun and(x: Short, y: Short): Short = not(nand(x, y))
fun or(x: Short, y: Short): Short = nand(not(x), not(y))
fun xor(x: Short, y: Short): Short = and(or(x, y), not(and(x, y)))
fun mux(x: Short, y: Short, sel: Boolean) = or(and(sel.toShort(), y), and(not(sel.toShort()), x))

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
fun add(x: Short, y: Short): Short = (x + y).toShort()
/*fun add(x: Short, y: Short): Short {
    val xb = x.toBinary()
    val yb = y.toBinary()

    val result = 0.toShort().toBinary()
    val (carry, number) = add(xb[Short.SIZE_BITS - 1], yb[Short.SIZE_BITS - 1])
    result[Short.SIZE_BITS - 1] = number

    var c = carry
    (Short.SIZE_BITS - 2 downTo 0).forEach {
        val (cr, n) = add(xb[it], yb[it], c)
        c = cr

        result[it] = n
    }

    return result
}*/

fun increment(x: Short): Short {
    return x.inc()
}

fun substract(x: Short, y: Short): Short {
    return increment(add(x, not(y)))
    //val not = not(y)
    //return 0
    //return increment(add(x, not))
}

fun zero(x: Short): Boolean = x == 0.toShort()
fun negative(x: Short): Boolean = x < 0.toShort()