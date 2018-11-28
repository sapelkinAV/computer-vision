import lab11.lab11
import lab3.lab3
import lab4.lab4
import lab8.lab8
import lab9.lab9
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs

fun main(args: Array<String>) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    lab3()
    lab4()
    lab8()
    lab9()
    lab11()

}