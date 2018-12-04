import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Tests {

    @Test
    fun nand() {
        assertFalse(nand(true, true))
        assertTrue(nand(true, false))
        assertTrue(nand(false, true))
        assertTrue(nand(false, false))
    }

    @Test
    operator fun not() {
        assertFalse(not(true))
        assertTrue(not(false))
    }

    @Test
    fun and() {
        assertTrue(and(true, true))
        assertFalse(and(true, false))
        assertFalse(and(false, true))
        assertFalse(and(false, false))
    }

    @Test
    fun or() {
        assertTrue(or(true, true))
        assertTrue(or(true, false))
        assertTrue(or(false, true))
        assertFalse(or(false, false))
    }

    @Test
    fun xor() {
        assertFalse(xor(true, true))
        assertTrue(xor(true, false))
        assertTrue(xor(false, true))
        assertFalse(xor(false, false))
    }

    @Test
    fun halfAdder() {
        assertTrue(add(true, true) == Pair(true, false))
        assertTrue(add(true, false) == Pair(false, true))
        assertTrue(add(false, true) == Pair(false, true))
        assertTrue(add(false, false) == Pair(false, false))
    }

    @Test
    fun fullAdder() {
        assertTrue(add(true, true, true) == Pair(true, true))
        assertTrue(add(true, true, false) == Pair(true, false))
        assertTrue(add(true, false, false) == Pair(false, true))
        assertTrue(add(false, false, false) == Pair(false, false))
    }

    @Test
    fun add16() {
        assertEquals(5.toShort(), add(2, 3))
        assertEquals(0.toShort(), add(-3, 3))
        assertEquals(4.toShort(), add(0, 4))
        assertEquals((-7).toShort(), add(5, -12))
    }

    @Test
    fun mux() {
        assertEquals(4, mux(1, 4, true))
        assertEquals(1, mux(1, 4, false))
    }

    @Test
    fun shortToBinary() {
        assertArrayEquals(BooleanArray(16) { it == 15 }, (1).toShort().toBinary())
        assertArrayEquals(BooleanArray(16) { false }, (0).toShort().toBinary())
        assertArrayEquals(BooleanArray(16) { true }, (-1).toShort().toBinary())
    }

    @Test
    fun binaryToShort() {
        assertEquals(1.toShort(), BooleanArray(16) { it == 15 }.toShort())
        assertEquals(0.toShort(), BooleanArray(16) { false }.toShort())
        assertEquals((-1).toShort(), BooleanArray(16) { true }.toShort())
    }

    @Test
    fun shortToBinaryToShort() {
        listOf(105, 99, 21, 12, 1, 0, -1, -12, -21, -99, -105)
                .map { it.toShort() }
                .forEach { assertEquals(it, it.toBinary().toShort()) }
    }

    @Test
    fun increment() {
        assertEquals(0.toShort(), increment(-1))
        assertEquals(1.toShort(), increment(0))
        assertEquals(2.toShort(), increment(1))
    }

    @Test
    fun substract() {
        assertEquals(0.toShort(), substract(1, 1))
        assertEquals((-1).toShort(), substract(0, 1))
        assertEquals((-1).toShort(), substract(10, 11))
        assertEquals((0).toShort(), substract(-1, -1))
        assertEquals((3).toShort(), substract(6, 3))
    }

    @Test
    fun `program counter not load`() {
        val pc = PC(Register(0))
        assertEquals(0, pc(5, false, false, false))
    }

    @Test
    fun `program counter load`() {
        val pc = PC(Register(0))
        assertEquals(5, pc(5, true, false, false))
    }

    @Test
    fun `program counter increment`() {
        val pc = PC(Register(0))
        assertEquals(1, pc(5, false, true, false))
    }

    @Test
    fun `program counter reset`() {
        val pc = PC(Register(5))
        assertEquals(0, pc(7, false, false, true))
    }


}