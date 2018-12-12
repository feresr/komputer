class Computer(private val rom: ROM,
               private val cpu: CPU,
               private val memory: RAM,
               private val reset: () -> Boolean) {

    init {
        //println("Starting pc... initial memory: $memory")
        //println("")
    }


    fun step() {
        //println("pc: ${cpu.currentPc}")
        cpu(
                inst = rom(cpu.currentPc),
                inM = memory(cpu.registerA()),
                reset = reset()
        )
        memory(input = cpu.outM, load = cpu.writeM, address = cpu.currentAddressM)
        //println("CPU: $cpu pc:${cpu.currentPc}") // - memory: $memory \n")
    }
}