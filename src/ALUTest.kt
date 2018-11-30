import org.testng.annotations.Test
import java.util.*
import kotlin.test.assertTrue

class ALUTest {

    val alu = ALU()

    @Test
    fun `zero`() {
        alu.zx = true
        alu.nx = false
        alu.zy = true
        alu.ny = false
        alu.f = true
        alu.no = false

        val x = BitSet(NBIT)
        x[0] = true

        val result = alu.compute(x, BitSet(NBIT))
        assertTrue(result.cardinality() == 0)
    }

    @Test
    fun `one`() {
        alu.zx = true
        alu.nx = true
        alu.zy = true
        alu.ny = true
        alu.f = true
        alu.no = true

        val x = BitSet(NBIT)
        x[0] = true

        val result = alu.compute(x, BitSet(NBIT))
        assertTrue(result.cardinality() == 1)
    }

    @Test
    fun `negative one`() {
        alu.zx = true
        alu.nx = true
        alu.zy = true
        alu.ny = false
        alu.f = true
        alu.no = false

        val x = BitSet(NBIT)
        x[0] = true

        val result = alu.compute(x, BitSet(NBIT))
        assertTrue(result.cardinality() == 16) //-1 in two's complement
    }

    @Test
    fun `first input`() {
        alu.zx = false
        alu.nx = false
        alu.zy = true
        alu.ny = true
        alu.f = false
        alu.no = false

        val x = BitSet(NBIT)
        x[0] = true

        val result = alu.compute(x, BitSet(NBIT))
        assertTrue(result == x) //-1 in two's complement
    }

    @Test
    fun `second input`() {
        alu.zx = true
        alu.nx = true
        alu.zy = false
        alu.ny = false
        alu.f = false
        alu.no = false

        val x = BitSet(NBIT)
        x[0] = true

        val y = BitSet(NBIT)
        x[2] = true
        x[5] = true

        val result = alu.compute(x, y)
        assertTrue(result == y)
    }

    @Test
    fun `addition x + y`() {
        alu.zx = false
        alu.nx = false
        alu.zy = false
        alu.ny = false
        alu.f = true
        alu.no = false

        val x = BitSet(NBIT)
        x[0] = true

        val y = BitSet(NBIT)
        x[2] = true
        x[5] = true

        val result = alu.compute(x, y)
        assertTrue(result == add(x,y))
    }

    @Test
    fun `and x && y`() {
        alu.zx = false
        alu.nx = false
        alu.zy = false
        alu.ny = false
        alu.f = false
        alu.no = false

        val x = BitSet(NBIT)
        x[0] = true

        val y = BitSet(NBIT)
        x[2] = true
        x[5] = true

        val result = alu.compute(x, y)
        assertTrue(result == and16(x,y))
    }
}