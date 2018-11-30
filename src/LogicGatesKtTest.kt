import org.testng.annotations.Test
import java.util.*
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LogicGatesKtTest {
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
        //1
        val x = BitSet(16)
        x[15] = true

        //1
        val y = BitSet(16)
        y[15] = true

        //2
        val expected = BitSet(16)
        expected[15] = false
        expected[14] = true

        val actual = add(x, y)
        print(actual)
        print(expected)

        assertTrue(actual == expected)
    }

    @Test
    fun `add16 s`() {
        //3
        val x = BitSet(16)
        x[15] = true
        x[14] = true

        //1
        val y = BitSet(16)
        y[15] = true

        //4
        val expected = BitSet(16)
        expected[13] = true

        val actual = add(x, y)
        print(actual)
        print(expected)

        assertTrue(actual == expected)
    }

    @Test
    fun increment() {
        //3
        val x = BitSet(16)
        x[15] = true
        x[14] = true

        //4
        val expected = BitSet(16)
        expected[13] = true

        val actual = increment(x)
        assertTrue(actual == expected)
    }

    @Test
    fun substractx() {
        //3
        val x = BitSet(16)
        x[15] = true
        x[14] = true

        //2
        val y = BitSet(16)
        y[14] = true

        //1
        val expected = BitSet(16)
        expected[15] = true

        val actual = substract(x, y)
        assertTrue(actual == expected)
    }

   /* @Test
    fun latch() {
        val clock1 = latch(true, true)
        print(clock1)
        val clock2 = latch(false, false, clock1)
        print(clock2)
        val clock3 = latch(false, true, clock1)
        print(clock3)
        val clock4 = latch(true, false, clock1)
        print(clock4)
    }*/

}