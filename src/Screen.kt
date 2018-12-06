import java.awt.*
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.util.concurrent.TimeUnit

class Screen(private val memory: ROM, fps: Int = 60, val onWindowClosed : ()-> Unit) : Frame("Monitor") {

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
                graphics?.dispose()
                dispose()
            }
        })
        setSize(WIDTH * SCALE + insets.left + insets.right, HEIGHT * SCALE + insets.top + insets.bottom)
        background = Color.BLACK
    }

    fun refresh() {
        if ((System.currentTimeMillis() - time) < step) return

        px = 0
        py = 0
        val gg = graphics as Graphics2D

        (0 until PIXELS).forEach { i ->
            memory(i.toShort())
                    .toBinary()
                    .map { Math.random() > .5 }
                    .forEach { bit ->
                        gg.paint = if (bit) Color.WHITE else Color.BLACK
                        gg.fillRect(px * SCALE + insets.left, py * SCALE + insets.top, SCALE, SCALE)
                        px++
                        if (px % WIDTH == 0) {
                            px = 0
                            py++
                        }
                    }

        }
        gg.dispose()
        time = System.currentTimeMillis()
    }

    companion object {
        const val HEIGHT = 256
        const val WIDTH = 512
        const val PIXELS = WIDTH * HEIGHT / Short.SIZE_BITS
        const val SCALE: Int = 3
    }
}