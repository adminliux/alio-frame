package ml.alio.frame.common

import org.junit.jupiter.api.Test
import java.io.File

class ImagesTest {
    @Test
    fun `test file parse base64`() {
        val resource = javaClass.classLoader.getResource("10.35.33.png")
        val parseImg = Base64Util.parseImg(Base64Util.getImageB64(File(resource.file)))
        println(parseImg)
    }
}