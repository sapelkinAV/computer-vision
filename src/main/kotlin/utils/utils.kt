package utils

import org.opencv.core.Mat
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import org.opencv.core.CvType.channels
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.core.MatOfByte
import java.io.IOException





const val PROJECT_PATH = "/Users/sapelkinav/.yandex.disk/498040059/Yandex.Disk.localized/code/learning/computer-vision"
const val RESOURCES_PATH = "$PROJECT_PATH/src/main/resources"
const val OUT_PATH = "$PROJECT_PATH/out"

fun convertBufferedImageToMat(image:BufferedImage): Mat {
    val imageMat = Mat()
    imageMat.put(0,0,(image.raster.dataBuffer as DataBufferByte).data)
    return imageMat
}

fun convertMatToBufferedImage(imageMat:Mat): BufferedImage {
    return BufferedImage(imageMat.width(), imageMat.height(), BufferedImage.TYPE_INT_RGB)
}


fun Mat.getByteArray(): ByteArray {
    val byteArray = ByteArray(width()* height() * elemSize().toInt())
    this.get(cols(),rows(),byteArray)
    return byteArray
}

fun Mat.getCopy() : Mat{
    val mat = Mat()
    copyTo(mat)
    return mat
}

fun matToBufferedImage(mat:Mat): BufferedImage {
    val out: BufferedImage
    val data = ByteArray(mat.width() * mat.height() * mat.elemSize().toInt())
    val type: Int = if (mat.channels() == 1)
        BufferedImage.TYPE_BYTE_GRAY
    else
        BufferedImage.TYPE_3BYTE_BGR
    mat.get(0, 0, data)
    out = BufferedImage(mat.width(), mat.height(), type)
    out.raster.setDataElements(0, 0, mat.width(), mat.height(), data)
    return out
}


fun matToBuffImage(matrix: Mat): BufferedImage {
    val mob = MatOfByte()
    Imgcodecs.imencode(".jpg", matrix, mob)
    return ImageIO.read(ByteArrayInputStream(mob.toArray()))
}

