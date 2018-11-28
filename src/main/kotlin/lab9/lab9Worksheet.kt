package lab9

import org.opencv.core.*
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import utils.RESOURCES_PATH
import java.util.ArrayList
import org.opencv.core.Mat
import utils.OUT_PATH
import org.opencv.core.Scalar
import org.opencv.core.CvType
import org.opencv.core.Core




fun lab9() {
    val hsvImg = Imgcodecs.imread("$RESOURCES_PATH/redkite.jpg")
    val thresholdImg = Mat()
    val hsvPlanes = ArrayList<Mat>()
    val thresh_type = Imgproc.THRESH_BINARY_INV // если убрать инв, дропну орла, но будет бэкграунд


    // threshold the image with the average hue value
    Core.split(hsvImg, hsvPlanes)

    // get the average hue value of the image
    val threshValue = getHistAverage(hsvImg, hsvPlanes.get(0))

    Imgproc.threshold(hsvPlanes.get(1), thresholdImg, threshValue, 60.0, thresh_type)



    // create the new image
    val foreground = Mat(hsvImg.size(), CvType.CV_8UC3, Scalar(255.0, 255.0, 255.0))
    hsvImg.copyTo(foreground, thresholdImg)
    Imgcodecs.imwrite("$OUT_PATH/lab9/redkite.jpg",foreground)

}

fun getHistAverage(hsvImg: Mat, hueValues: Mat): Double {
    // init
    var average = 0.0
    val hist_hue = Mat()
    // 0-180: range of Hue values
    val histSize = MatOfInt(180)
    val hue = ArrayList<Mat>()
    hue.add(hueValues)

    // compute the histogram
    Imgproc.calcHist(hue, MatOfInt(0), Mat(), hist_hue, histSize, MatOfFloat(0F, 179F))

    // get the average Hue value of the image
    // (sum(bin(h)*h))/(image-height*image-width)
    // -----------------
    // equivalent to get the hue of each pixel in the image, add them, and
    // divide for the image size (height and width)
    for (h in 0..179) {
        // for each bin, get its value and multiply it for the corresponding
        // hue
        average += hist_hue.get(h, 0)[0] * h
    }

    // return the average hue of the image
    return average / hsvImg.size().height / hsvImg.size().width
}