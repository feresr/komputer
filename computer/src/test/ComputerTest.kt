import org.junit.Assert.assertEquals
import org.junit.Test

class ComputerTest {

    @Test
    fun `@ instruction`() {
        val program = ShortArray(4)

        program[0] = 5
        program[1] = -5104
        program[2] = 0
        program[3] = -7416

        val ram = VideoRam()
        val aRegister = Register()
        val dRegister = Register()
        val computer = Computer(
                rom = Cartridge(program),
                cpu = CPU(PC(Register()), ALU(), aRegister, dRegister),
                memory = ram,
                reset = { false }
        )

        computer.step()
        assertEquals(5.toShort(), aRegister())
        computer.step()
        assertEquals(5.toShort(), dRegister())
        computer.step()
        assertEquals(0.toShort(), aRegister())
        computer.step()
        assertEquals(ram(aRegister()), dRegister())
    }

    @Test
    fun `jump logic`() {

        val runTime = Runtime.getRuntime()


        val program = ShortArray(4)

        program[0] = 5
        program[1] = -5104
        program[2] = 0
        program[3] = -7416

        val ram = VideoRam()
        val aRegister = Register()
        val dRegister = Register()
        val computer = Computer(
                rom = Cartridge(program),
                cpu = CPU(PC(Register()), ALU(), aRegister, dRegister),
                memory = ram,
                reset = { false }
        )

        computer.step()
        assertEquals(5.toShort(), aRegister())
        computer.step()
        assertEquals(5.toShort(), dRegister())
        computer.step()
        assertEquals(0.toShort(), aRegister())
        computer.step()
        assertEquals(ram(aRegister()), dRegister())
    }
}