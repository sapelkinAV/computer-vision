package lab3

import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import utils.*
import java.awt.image.BufferedImage
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.MatOfInt
import org.opencv.core.MatOfFloat
import org.opencv.core.Scalar
import org.opencv.core.CvType
import java.util.ArrayList




fun lab3(){
    val frame = object: OpenCVFrame() {
        override fun processImage(mat: Mat): BufferedImage {
            val borderSize = 80
            //val temp = Mat(mat.rows(),mat.cols(),CvType.CV_8UC3)


            //histograms
            contentPane.graphics.drawImage(matToBuffImage(getHistImage(mat)),1280,0,this)


            // Equalization
            val temp = Mat()
            Imgproc.cvtColor(mat,temp,Imgproc.COLOR_BGR2YCrCb)
            val channels = ArrayList<Mat>()
            Core.split(temp, channels)



            Imgproc.equalizeHist(channels[0],channels[0])
            Core.merge(channels,temp)
            Imgproc.cvtColor(temp,temp,Imgproc.COLOR_BGR2RGB)

            return matToBuffImage(temp)
        }
    }
    val claheFrame = object: OpenCVFrame(){
        init {
            title = "CLAHE"
        }
        override fun processImage(mat: Mat): BufferedImage {
           val channels =  ArrayList<Mat>()
            Core.split(mat, channels)
            val clahe = Imgproc.createCLAHE()
            val destImage =  Mat(mat.height(),mat.width(), CvType.CV_8UC4)
            clahe.apply(channels[0], destImage)
            Core.merge(channels, mat)
            return matToBuffImage(destImage)
        }
    }
    frame.isVisible = true
    claheFrame.isVisible = true



}

fun getHistImage(mat:Mat): Mat {
    val bgrPlanes = ArrayList<Mat>()
    Core.split(mat, bgrPlanes)
    val histSize = 256
    val range = floatArrayOf(0f, 256f) //the upper boundary is exclusive
    val histRange = MatOfFloat(*range)
    val accumulate = false
    val bHist = Mat()
    val gHist = Mat()
    val rHist = Mat()
    Imgproc.calcHist(bgrPlanes, MatOfInt(0), Mat(), bHist, MatOfInt(histSize), histRange, accumulate)
    Imgproc.calcHist(bgrPlanes, MatOfInt(1), Mat(), gHist, MatOfInt(histSize), histRange, accumulate)
    Imgproc.calcHist(bgrPlanes, MatOfInt(2), Mat(), rHist, MatOfInt(histSize), histRange, accumulate)
    val histW = 512
    val histH = 400
    val binW = Math.round(histW.toDouble() / histSize).toInt()
    val histImage = Mat(histH, histW, CvType.CV_8UC3, Scalar(0.0, 0.0, 0.0))
    Core.normalize(bHist, bHist, 0.0, histImage.rows().toDouble(), Core.NORM_MINMAX)
    Core.normalize(gHist, gHist, 0.0, histImage.rows().toDouble(), Core.NORM_MINMAX)
    Core.normalize(rHist, rHist, 0.0, histImage.rows().toDouble(), Core.NORM_MINMAX)
    val bHistData = FloatArray((bHist.total() * bHist.channels()).toInt())
    bHist.get(0, 0, bHistData)
    val gHistData = FloatArray((gHist.total() * gHist.channels()).toInt())
    gHist.get(0, 0, gHistData)
    val rHistData = FloatArray((rHist.total() * rHist.channels()).toInt())
    rHist.get(0, 0, rHistData)
    for (i in 1 until histSize) {
        Imgproc.line(histImage, Point((binW * (i - 1)).toDouble(), (histH - Math.round(bHistData[i - 1])).toDouble()),
                Point((binW * i).toDouble(), (histH - Math.round(bHistData[i])).toDouble()), Scalar(255.0, 0.0, 0.0), 2)
        Imgproc.line(histImage, Point((binW * (i - 1)).toDouble(), (histH - Math.round(gHistData[i - 1])).toDouble()),
                Point((binW * i).toDouble(), (histH - Math.round(gHistData[i])).toDouble()), Scalar(0.0, 255.0, 0.0), 2)
        Imgproc.line(histImage, Point((binW * (i - 1)).toDouble(), (histH - Math.round(rHistData[i - 1])).toDouble()),
                Point((binW * i).toDouble(), (histH - Math.round(rHistData[i])).toDouble()), Scalar(0.0, 0.0, 255.0), 2)
    }
    return histImage
}

