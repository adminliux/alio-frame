package ml.alio.frame.common

import Decoder.BASE64Decoder
import Decoder.BASE64Encoder
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream


/**
 * base64 编码
 * Created by 留下 on 2017/2/20.
 */
object Base64Util {
    /**
     * 字符串[src]base64编码
     */
    fun encoder(src: String) = encoder(src.toByteArray())

    /**
     * 字符串[src]base64编码
     */
    fun encoder(src: ByteArray) = BASE64Encoder().encode(src)!!

    //base64字符串转化成图片
    fun parseImg(imgStr: String): File {
        val decoder = BASE64Decoder()
        val file = File(System.getProperty("user.dir") + File.separator +
                System.currentTimeMillis() + "." + getBase64Format(imgStr))
        val write = FileOutputStream(file)
        val decoderBytes = decoder.decodeBuffer(imgStr.substring(imgStr.indexOf(",") + 1, imgStr.length))
        write.write(decoderBytes)
        write.close()

        return file
    }

    /**
     * 获取b64编码文件格式
     */
    private fun getBase64Format(b64: String) = b64.substring(b64.indexOf("/") + 1, b64.indexOf(";"))


    //图片转化成base64字符串
    fun getImageB64(imgFile: File): String {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        val `in`: InputStream = FileInputStream(imgFile)
        val data: ByteArray = ByteArray(`in`.available())
        //读取图片字节数组
        `in`.read(data)
        `in`.close()

        //对字节数组Base64编码
        val encoder = BASE64Encoder()
        val name = imgFile.name
        //返回Base64编码过的字节数组字符串
        return "data:image/${name.substring(name.lastIndexOf(".") + 1)};base64," + encoder.encode(data)
    }
}