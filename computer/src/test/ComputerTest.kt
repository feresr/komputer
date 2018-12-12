import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File
import kotlin.streams.toList

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
        val program = assembleFile(File("src/test/programs/JMP")).toList().toShortArray()

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
        assertEquals(1.toShort(), dRegister())
        computer.step()
        assertEquals(2.toShort(), dRegister())
        computer.step()
        assertEquals(0.toShort(), aRegister())
        computer.step()
        assertEquals(ram(aRegister()), dRegister())
        computer.step()
        assertEquals(1.toShort(), aRegister())
        computer.step()
        assertEquals(1.toShort(), aRegister())
    }

    @Test
    fun `JGT`() {
        val program = assembleFile(File("src/test/programs/JGT")).toList().toShortArray()

        val ram = VideoRam()
        val aRegister = Register()
        val dRegister = Register()
        val pc = Register()
        val computer = Computer(
                rom = Cartridge(program),
                cpu = CPU(PC(pc), ALU(), aRegister, dRegister),
                memory = ram,
                reset = { false }
        )

        computer.step()
        assertEquals(1.toShort(), pc())
        computer.step()
        assertEquals(0.toShort(), pc())
        computer.step()
        assertEquals(1.toShort(), pc())
    }

    @Test
    fun JEQ() {
        val program = assembleFile(File("src/test/programs/JEQ")).toList().toShortArray()

        val ram = VideoRam()
        val aRegister = Register()
        val dRegister = Register()
        val pc = Register()
        val computer = Computer(
                rom = Cartridge(program),
                cpu = CPU(PC(pc), ALU(), aRegister, dRegister),
                memory = ram,
                reset = { false }
        )

        computer.step()
        assertEquals(0.toShort(), pc())
        computer.step()
        assertEquals(0.toShort(), pc())
        computer.step()
        assertEquals(0.toShort(), pc())
    }

    @Test
    fun JGE() {
        val program = assembleFile(File("src/test/programs/JGE")).toList().toShortArray()

        val ram = VideoRam()
        val aRegister = Register()
        val dRegister = Register()
        val pc = Register()
        val computer = Computer(
                rom = Cartridge(program),
                cpu = CPU(PC(pc), ALU(), aRegister, dRegister),
                memory = ram,
                reset = { false }
        )

        computer.step()
        computer.step()
        computer.step()
        computer.step()
        assertEquals(3.toShort(), pc())
        computer.step()
        assertEquals(3.toShort(), pc())
        computer.step()
        assertEquals(4.toShort(), pc())
    }

    @Test
    fun JLT() {
        val program = assembleFile(File("src/test/programs/JLT")).toList().toShortArray()

        val ram = VideoRam()
        val aRegister = Register()
        val dRegister = Register()
        val pc = Register()
        val computer = Computer(
                rom = Cartridge(program),
                cpu = CPU(PC(pc), ALU(), aRegister, dRegister),
                memory = ram,
                reset = { false }
        )

        computer.step()
        computer.step()
        computer.step()
        computer.step()
        assertEquals(4.toShort(), pc())
        computer.step()
        assertEquals(4.toShort(), pc())
        computer.step()
        assertEquals(5.toShort(), pc())
    }
}