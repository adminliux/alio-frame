package ml.alio.frame.common

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TemplateTest {
    @Test
    fun `velocity Compile`() {
        val velocityCompile = velocityCompile("==\$_.hello", mapOf("hello" to "11"))
        Assertions.assertEquals(velocityCompile, "==11")
    }
}