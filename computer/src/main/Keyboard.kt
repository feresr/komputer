import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class Keyboard(private val memory: Register) : KeyListener {

    override fun keyTyped(e: KeyEvent) {
    }

    override fun keyPressed(e: KeyEvent) {
        memory(e.keyCode.toKey(), true)
    }

    //TODO: MAP CORRECT SCANKEY
    private fun Int.toKey() = when(this) {
        37 -> 130
        39 -> 132
        27 -> 140
        else -> this
    }.toShort()

    override fun keyReleased(e: KeyEvent?) {
        memory(0.toShort(), true)
    }
}