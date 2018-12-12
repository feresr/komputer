import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class Keyboard(private val memory: Register) : KeyListener {
    override fun keyTyped(e: KeyEvent?) {}

    override fun keyPressed(e: KeyEvent) {
        println("scan code ${e.paramString().split(",")}")
        memory(e.paramString().split(",")[6].split("=")[1].toShort(), true)
    }

    override fun keyReleased(e: KeyEvent?) {
        memory(0, true)
    }
}