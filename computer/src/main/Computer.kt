class Computer(rom: ROM, cpu: CPU, memory: RAM, reset: () -> Boolean, clock : () -> Unit, computerOn : () -> Boolean) {

    init {

        println("Starting pc... initial memory: $memory")
        println("")

        while (computerOn()) {

            println("pc: ${cpu.currentPc} - CPU: $cpu")
            cpu(
                    inst = rom(cpu.currentPc),
                    inM = memory(input = cpu.outM, load = cpu.writeM, address = cpu.currentAddressM),
                    reset = reset()
            )
            println("memory: $memory \n")

            clock()
            //Thread.sleep(1000)
        }
    }
}