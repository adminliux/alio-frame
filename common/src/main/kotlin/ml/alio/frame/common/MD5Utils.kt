package ml.alio.frame.common

import java.security.MessageDigest

/**
 * Modified By:
 */
object MD5Utils {

    private val hexDigIts = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f")

    /**
     * MD5加密
     *
     * @param origin      字符
     * @param charsetName 编码
     */
    fun encode(origin: String, charsetName: String = "UTF-8"): String {
        var resultString: String?
        try {
            resultString = origin
            val md = MessageDigest.getInstance("MD5")
            resultString = if ("" == charsetName) {
                byteArrayToHexString(md.digest(resultString.toByteArray()))
            } else {
                byteArrayToHexString(md.digest(resultString.toByteArray(charset(charsetName))))
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        return resultString
    }


    private fun byteArrayToHexString(b: ByteArray): String {
        val resultSb = StringBuilder()
        for (aB in b) {
            resultSb.append(byteToHexString(aB))
        }
        return resultSb.toString()
    }

    private fun byteToHexString(b: Byte): String {
        var n = b.toInt()
        if (n < 0) {
            n += 256
        }
        val d1 = n / 16
        val d2 = n % 16
        return hexDigIts[d1] + hexDigIts[d2]
    }

}