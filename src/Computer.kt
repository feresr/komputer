class Computer(rom: ROM32K, cpu: CPU, memory: RAM, reset: () -> Boolean, clock : () -> Unit) {

    init {
        println("Starting pc... initial memory: $memory")
        println("")

        while (true) {
            println("pc: ${cpu.currentPc} - memory: $memory")
            println("CPU: $cpu")
            println("")
            cpu(
                    inst = rom(cpu.currentPc),
                    inM = memory(input = cpu.outM, load = cpu.writeM, address = cpu.currentAddressM),
                    reset = reset()
            )

            clock()
            Thread.sleep(200)
        }
    }
}