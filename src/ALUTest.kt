import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ALUTest {
    val alu = ALU()

    @Test
    fun `zero`() {
        val result = alu(1, 2, true, false, true, false, true, false)
        assertEquals(0.toShort(), result)
    }

    @Test
    fun `one`() {
        val result = alu(1, 2, true, true, true, true, true, true)
        assertEquals(1.toShort(), result)
    }

    @Test
    fun `negative one`() {
        val result = alu(1, 2, true, true, true, false, true, false)
        assertEquals((-1).toShort(), result)
    }

    @Test
    fun `first input`() {
        val result = alu(8, 2, false, false, true, true, false, false)
        assertEquals(8.toShort(), result)
    }

    @Test
    fun `second input`() {
        val result = alu(8, 2, true, true, false, false, false, false)
        assertEquals(2.toShort(), result)
    }

    @Test
    fun `addition x + y`() {
        val result = alu(8, 2, false, false, false, false, true, false)
        assertEquals((2 + 8).toShort(), result)
    }

    @Test
    fun `and x && y`() {
        val result = alu(8, 2, false, false, false, false, false, false)
        assertEquals(and(8, 2), result)
    }
}