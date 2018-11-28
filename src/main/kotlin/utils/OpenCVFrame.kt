package utils

import org.opencv.core.Mat
import java.awt.Graphics
import java.awt.image.BufferedImage
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

open class OpenCVFrame : JFrame() {

    val contentPane: JPanel

    private val videoCap = VideoCap()

    init {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setBounds(0, 0, 1680, 2000)
        contentPane = JPanel()
        contentPane.border = EmptyBorder(0, 0, 0, 0)
        setContentPane(contentPane)
        contentPane.layout = null

        MyThread().start()
    }

    override fun paint(g: Graphics?) {
        val contentGraphics = contentPane.graphics
        val image = processImage(videoCap.readMatFromCamera())
        contentGraphics.drawImage(image, 0, 0, this)
    }

    internal inner class MyThread : Thread() {
        override fun run() {
            while (true) {
                repaint()
                try {
                    Thread.sleep(1)
                } catch (e: InterruptedException) {
                }

            }
        }
    }

    open fun processImage(image:BufferedImage): BufferedImage {
        return image
    }
    open fun processImage(mat:Mat):BufferedImage{
        return Mat2Image().getImage(mat)
    }



}