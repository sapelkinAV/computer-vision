package lab8

import org.opencv.core.Core
import org.opencv.core.CvType.CV_8UC1
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import utils.OUT_PATH
import utils.RESOURCES_PATH


fun lab8(){
    val imgOriginal = Imgcodecs.imread("$RESOURCES_PATH/aleksandr-nevskij-24.jpg",CV_8UC1)
    val thresholded = Mat()
    Imgproc.adaptiveThreshold(imgOriginal,thresholded,255.0,Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,Imgproc.THRESH_BINARY,11,2.0)
    Imgcodecs.imwrite("$OUT_PATH/lab8/thresholded.jpg",thresholded)
}