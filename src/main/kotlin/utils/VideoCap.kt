package utils

import java.awt.image.BufferedImage
import org.opencv.videoio.VideoCapture
import org.opencv.core.Mat


class VideoCap internal constructor() {

    private val cap: VideoCapture = VideoCapture()

    fun readMatFromCamera(): Mat {
        return cap.readMat()
    }

    fun readImageFromCamera(): BufferedImage{
        return matToBuffImage(cap.readMat())
    }


    init {
        cap.open(0)
    }
}

fun VideoCapture.readMat(): Mat {
    val mat = Mat()
    read(mat)
    return mat
}