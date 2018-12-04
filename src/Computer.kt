class Computer(rom: ROM32K, cpu: CPU, memory: RAM8, reset: () -> Boolean) {

    init {
        while (true) {
            cpu(
                    inst = rom(cpu.currentPc),
                    inM = memory(input = cpu.outM, load = cpu.writeM, address = cpu.currentAddressM),
                    reset = reset()
            )
            println(memory)
            Thread.sleep(300)
        }
    }
}