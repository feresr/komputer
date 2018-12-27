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
 * -1 in binary = 1111111111111111
 * 1 in binary = 00000000000000001
 */
fun Boolean.toShort(): Short = if (this) -1 else 0

fun BooleanArray.toShort(): Short {
    var c = 1
    val firstBits = (Short.SIZE_BITS - 1 downTo 1).fold(0) { acc, cur ->
        acc + c * (if (this[cur]) 1 else 0).also { c *= 2 }
    }

    return (if (this[0]) firstBits - Short.MIN_VALUE else firstBits).toShort()
}


// Overcomplicated way I came up to read bites out of Shorts in two's complement. Using bite-wise operations now.
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

operator fun Short.get(position : Int) : Boolean {
    return (this.toInt() shr position) and 1 == 1
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
fun add(x: Boolean, y: Boolean): Boolean = xor(x, y)
fun carry(x: Boolean, y: Boolean): Boolean = and(x, y)

//full adder
fun add(x: Boolean, y: Boolean, z: Boolean): Boolean {
    val b = add(x, y)
    return add(b, z)
}

fun carry(x: Boolean, y: Boolean, z: Boolean): Boolean {
    val a = carry(x, y)
    val b = add(x, y)
    val c = carry(b, z)
    return or(c, a)
}

fun add(x: Short, y: Short): Short {

    val result = BooleanArray(16)
    val number = add(x[0], y[0])
    val carry = carry(x[0], y[0])
    result[Short.SIZE_BITS - 1] = number

    var c = carry
    (1 until  Short.SIZE_BITS).forEach {
        val n = add(x[it], y[it], c)
        val cr = carry(x[it], y[it], c)
        c = cr
        result[Short.SIZE_BITS - 1 - it] = n
    }

    return result.toShort()
}


fun increment(x: Short): Short {
    return x.inc()
}

fun substract(x: Short, y: Short): Short {
    return increment(add(x, not(y)))
}

fun zero(x: Short): Boolean = x == 0.toShort()
fun negative(x: Short): Boolean = x < 0.toShort()