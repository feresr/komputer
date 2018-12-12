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

    @Test
    fun `D`() {
        val program = assembleFile(File("src/test/programs/D")).toList().toShortArray()

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

        assertEquals(0.toShort(), dRegister())
        computer.step()
        assertEquals(1.toShort(), dRegister())
        assertEquals(0.toShort(), aRegister())
        computer.step()
        assertEquals(2.toShort(), dRegister())
        assertEquals(0.toShort(), aRegister())
        computer.step()
        assertEquals(1.toShort(), dRegister())
        assertEquals(0.toShort(), aRegister())
    }

    @Test
    fun `M`() {
        val program = assembleFile(File("src/test/programs/M")).toList().toShortArray()

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

        assertEquals(0.toShort(), ram(aRegister()))
        computer.step()
        assertEquals(1.toShort(), ram(aRegister()))
        assertEquals(0.toShort(), aRegister())
        computer.step()
        assertEquals(2.toShort(), ram(aRegister()))
        assertEquals(0.toShort(), aRegister())
        computer.step()
        assertEquals(1.toShort(), ram(aRegister()))
        assertEquals(0.toShort(), aRegister())
    }

    @Test
    fun `A`() {
        val program = assembleFile(File("src/test/programs/A")).toList().toShortArray()

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

        assertEquals(0.toShort(), aRegister())
        computer.step()
        assertEquals(1.toShort(), aRegister())
        assertEquals(0.toShort(), dRegister())
        computer.step()
        assertEquals(2.toShort(), aRegister())
        assertEquals(0.toShort(), dRegister())
        computer.step()
        assertEquals(1.toShort(), aRegister())
        assertEquals(0.toShort(), dRegister())
    }


    @Test
    fun `AND`() {
        val program = assembleFile(File("src/test/programs/AND")).toList().toShortArray()

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
        assertEquals(4.toShort(), dRegister())

    }

    @Test
    fun `OR`() {
        val program = assembleFile(File("src/test/programs/OR")).toList().toShortArray()

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
        assertEquals(7.toShort(), dRegister())

    }

    @Test
    fun `MINUS`() {
        val program = assembleFile(File("src/test/programs/MINUS")).toList().toShortArray()

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
        assertEquals(18.toShort(), ram(aRegister()))
        computer.step()
        assertEquals((-12).toShort(), ram(aRegister()))

    }


    @Test
    fun `NOT`() {
        val program = assembleFile(File("src/test/programs/NOT")).toList().toShortArray()

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
        assertEquals((-1).toShort(), dRegister())
        computer.step()
        assertEquals(0.toShort(), dRegister())

    }

    @Test
    fun `DEST`() {
        val program = assembleFile(File("src/test/programs/DEST")).toList().toShortArray()

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
        assertEquals((10).toShort(), dRegister())
        assertEquals((10).toShort(), ram(aRegister()))
        computer.step()
        assertEquals((11).toShort(), dRegister())
        assertEquals((11).toShort(), aRegister())
        assertEquals((11).toShort(), ram(10.toShort()))

    }

    @Test
    fun `KEYBOARD`() {
        val program = assembleFile(File("src/test/programs/KEYBOARD")).toList().toShortArray()

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
        assertEquals((1).toShort(), ram.keyboardMemory())

    }
}