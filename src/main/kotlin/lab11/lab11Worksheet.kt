package lab11

import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import utils.OUT_PATH
import utils.RESOURCES_PATH


fun opening(){
    val kernelMatrix = DoubleArray(3,init = {
        0.3
    })
    val kernel = Mat(3,3, CvType.CV_32F, Scalar(kernelMatrix))
    val image = Imgcodecs.imread("$RESOURCES_PATH/dots.jpg",org.opencv.core.CvType.CV_8U)
    val output = Mat()

    Imgproc.erode(image,output, kernel, Point(-1.0,-1.0),5)
    Imgproc.dilate(image,output, kernel, Point(-1.0,-1.0),5)

    Imgcodecs.imwrite("$OUT_PATH/lab11/closing.jpg",output)
}
fun closing(){
    val kernelMatrix = DoubleArray(3,init = {
        0.3
    })
    val kernel = Mat(3,3, CvType.CV_32F, Scalar(kernelMatrix))
    val image = Imgcodecs.imread("$RESOURCES_PATH/dots.jpg",org.opencv.core.CvType.CV_8U)
    val output = Mat()

    Imgproc.dilate(image,output, kernel, Point(-1.0,-1.0),5)
    Imgproc.erode(image,output, kernel, Point(-1.0,-1.0),5)

    Imgcodecs.imwrite("$OUT_PATH/lab11/opening.jpg",output)
}
fun lab11(){
    opening()
    closing()

}