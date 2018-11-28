package lab4

import org.opencv.core.Core
import org.opencv.core.CvType.CV_32F
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import utils.OUT_PATH
import utils.RESOURCES_PATH


fun copyOnWrite(){
    val borderSize = 50

    val imgOriginal = Imgcodecs.imread("$RESOURCES_PATH/aleksandr-nevskij-24.jpg")
    val imgBordered = Mat()
    Core.copyMakeBorder(imgOriginal, imgBordered, borderSize, borderSize, borderSize, borderSize, Core.BORDER_REPLICATE)
    Imgcodecs.imwrite("$OUT_PATH/lab4/nevsky.jpg",imgBordered)


}

fun brightness() {

    val kernelMatrix = DoubleArray(3,init = {
        0.5
    })
    val kernel = Mat(3,3,CV_32F, Scalar(kernelMatrix))
    val imgOriginal = Imgcodecs.imread("$RESOURCES_PATH/aleksandr-nevskij-24.jpg")
    val imgBrighted = Mat()

    Imgproc.filter2D(imgOriginal,imgBrighted,imgOriginal.depth(),kernel, Point(-1.0,-1.0))
    Imgcodecs.imwrite("$OUT_PATH/lab4/nevsky_filtered.jpg",imgBrighted)
}



fun lab4(){
    copyOnWrite()
    brightness()
}