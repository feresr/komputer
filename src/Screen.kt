import java.awt.*
import java.awt.image.BufferedImage


class Screen(private val memory: RAM8K) : Frame("Monitor") {

    // i = 32 * row + col / 16
    // col % 16
    private val image = BufferedImage(512, 256, BufferedImage.TYPE_BYTE_BINARY)

    init {
        //isUndecorated = true
        isVisible = true
        isResizable = true
        setSize(512 + insets.left + insets.right, 256 + insets.top + insets.bottom)
    }

    override fun paint(g: Graphics) {
        var x = 0
        var y = 0
        (0 until 512*256/16).forEach { i ->
            memory(0, false, i.toShort()).toBinary().forEach {
                image.setRGB(x, y, if (it) Color.WHITE.rgb else Color.black.rgb)
                x++
                if (x % 512 == 0) {
                    x = 0
                    y++
                }
            }

        }

        g.drawImage(image, insets.left, insets.top, null)
    }
}