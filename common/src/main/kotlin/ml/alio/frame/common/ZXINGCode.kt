package ml.alio.frame.common

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import org.apache.commons.lang3.StringUtils
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO

/**
 * 画制定logo和制定描述的二维码
 **/
object ZXINGCode {
    private const val QRCOLOR = -0x1000000 // 默认是黑色
    private const val BGWHITE = -0x1 // 背景颜色

    private const val WIDTH = 400 // 二维码宽
    private const val HEIGHT = 400 // 二维码高

    // 用于设置QR二维码参数
    private val hints = object : HashMap<EncodeHintType, Any>() {
        init {
            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H)// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
            put(EncodeHintType.CHARACTER_SET, "utf-8")// 设置编码方式
            put(EncodeHintType.MARGIN, 0)
        }
    }

    /**
     * @param logoFile LOGO文件
     * @param codeFile 生成文件路径
     * @param qrUrl    内容
     * @param note     备注
     */
    // 生成带logo的二维码图片
    fun drawLogoQRCode(
            logoFile: File,
            codeFile: File,
            qrUrl: String,
            note: String,
            //barColor: String = QRCOLOR.toString(),
            //backgroundColor: String = BGWHITE.toString(),
            width_: Int = WIDTH,
            height_: Int = HEIGHT
    ): File {
        try {
            val multiFormatWriter = MultiFormatWriter()
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            val bm = multiFormatWriter.encode(qrUrl, BarcodeFormat.QR_CODE, width_, height_, hints)
            var image = BufferedImage(width_, height_, BufferedImage.TYPE_INT_RGB)

            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (x in 0 until WIDTH) {
                for (y in 0 until HEIGHT) {

                    image.setRGB(x, y, if (bm.get(x, y)) QRCOLOR else BGWHITE)
                }
            }

            val width = image.width
            val height = image.height
            if (Objects.nonNull(logoFile) && logoFile.exists()) {
                // 构建绘图对象
                val g = image.createGraphics()
                // 读取Logo图片
                val logo = ImageIO.read(logoFile)
                // 开始绘制logo图片
                g.drawImage(logo, width * 2 / 5, height * 2 / 5, width * 2 / 10, height * 2 / 10, null)
                g.dispose()
                logo.flush()
            }

            // 自定义文本描述
            if (StringUtils.isNotEmpty(note)) {
                // 新的图片，把带logo的二维码下面加上文字
                var outImage = BufferedImage(400, 445, BufferedImage.TYPE_4BYTE_ABGR)
                val outg = outImage.createGraphics()
                // 画二维码到新的面板
                outg.drawImage(image, 0, 0, image.width, image.height, null)
                // 画文字到新的面板
                outg.color = Color.BLACK
                outg.font = Font("楷体", Font.BOLD, 30) // 字体、字型、字号
                val strWidth = outg.fontMetrics.stringWidth(note)
                if (strWidth > 399) {
                    // //长度过长就截取前面部分
                    // 长度过长就换行
                    val note1 = note.substring(0, note.length / 2)
                    val note2 = note.substring(note.length / 2, note.length)
                    val strWidth1 = outg.fontMetrics.stringWidth(note1)
                    val strWidth2 = outg.fontMetrics.stringWidth(note2)
                    outg.drawString(note1, 200 - strWidth1 / 2, height + (outImage.height - height) / 2 + 12)
                    val outImage2 = BufferedImage(400, 485, BufferedImage.TYPE_4BYTE_ABGR)
                    val outg2 = outImage2.createGraphics()
                    outg2.drawImage(outImage, 0, 0, outImage.width, outImage.height, null)
                    outg2.color = Color.BLACK
                    outg2.font = Font("宋体", Font.BOLD, 30) // 字体、字型、字号
                    outg2.drawString(note2, 200 - strWidth2 / 2, outImage.height + (outImage2.height - outImage.height) / 2 + 5)
                    outg2.dispose()
                    outImage2.flush()
                    outImage = outImage2
                } else {
                    outg.drawString(note, 200 - strWidth / 2, height + (outImage.height - height) / 2 + 12) // 画文字
                }
                outg.dispose()
                outImage.flush()
                image = outImage
            }

            image.flush()

            ImageIO.write(image, "png", codeFile)
            return codeFile
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

}