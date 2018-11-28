package utils

import java.awt.image.DataBufferByte
import java.awt.image.WritableRaster
import org.opencv.core.Mat
import java.awt.image.BufferedImage
import org.opencv.core.Core
import org.opencv.core.CvType




class Mat2Image {

    internal var mat = Mat()
    internal var img: BufferedImage? = null

    constructor() {}

    constructor(mat: Mat) {
        getSpace(mat)
    }

    fun getSpace(mat: Mat) {
        var type = 0
        if (mat.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY
        } else if (mat.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR
        }
        this.mat = mat
        val w = mat.cols()
        val h = mat.rows()
        if (img == null || img!!.width != w || img!!.height != h || img!!.type != type)
            img = BufferedImage(w, h, type)
    }

    internal fun getImage(mat: Mat): BufferedImage {
        getSpace(mat)
        val raster = img!!.raster
        val dataBuffer = raster.dataBuffer as DataBufferByte
        val data = dataBuffer.data
        mat.get(0, 0, data)
        return img as BufferedImage
    }



    companion object {

        init {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
        }

        fun getMat(bufferedImage: BufferedImage, type:Int=CvType.CV_8U): Mat {
            val mat = Mat(bufferedImage.height, bufferedImage.width, type)
            val data = (bufferedImage.raster.dataBuffer as DataBufferByte).data
            mat.put(0, 0, data)
            return mat
        }
    }


}