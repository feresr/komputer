import java.awt.Color
import java.awt.Frame
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.util.concurrent.TimeUnit

class Screen(private val memory: ROM, fps: Int = 60, val onWindowClosed: () -> Unit) : Frame("Monitor") {

    private var px = 0
    private var py = 0

    private var time = System.currentTimeMillis()
    private val step = TimeUnit.SECONDS.toMillis(1) / fps

    init {
        isResizable = false
        isVisible = true
        addWindowListener(object : WindowListener {
            override fun windowDeiconified(e: WindowEvent?) {}
            override fun windowClosed(e: WindowEvent?) {}
            override fun windowActivated(e: WindowEvent?) {}
            override fun windowDeactivated(e: WindowEvent?) {}
            override fun windowOpened(e: WindowEvent?) {}
            override fun windowIconified(e: WindowEvent?) {}
            override fun windowClosing(e: WindowEvent?) {
                onWindowClosed()
            }
        })
        setSize(WIDTH * SCALE + insets.left + insets.right, HEIGHT * SCALE + insets.top + insets.bottom)

        //TODO: does not work on mac os, find out why
        background = Color.white
    }

    fun refresh() {
        if ((System.currentTimeMillis() - time) < step) return
        if (bufferStrategy == null) createBufferStrategy(2)

        px = 0
        py = 0
        val gg = bufferStrategy.drawGraphics //as Graphics2D
        gg.color = Color.BLACK
        gg.clearRect(insets.left, insets.top, WIDTH * SCALE + insets.left + insets.right, HEIGHT * SCALE + insets.top + insets.bottom)
        (0 until PIXELS).forEach { i ->
            val value = memory(i.toShort())
            //16 bites
            (0 until Short.SIZE_BITS).map { (value.toInt() shr it) and 1 == 1 }
                    .forEach { bit ->
                        if (bit) gg.fillRect(px * SCALE + insets.left, py * SCALE + insets.top, SCALE, SCALE)
                        px++
                        if (px % WIDTH == 0) {
                            px = 0
                            py++
                        }
                    }
        }

        gg.dispose()
        bufferStrategy.show()
        time = System.currentTimeMillis()
    }

    companion object {
        const val HEIGHT = 256
        const val WIDTH = 512
        const val PIXELS = WIDTH * HEIGHT / Short.SIZE_BITS
        const val SCALE: Int = 2
    }
}