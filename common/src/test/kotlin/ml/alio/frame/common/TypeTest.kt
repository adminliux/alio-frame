package ml.alio.frame.common

import ml.alio.frame.common.type.Remark
import ml.alio.frame.common.type.remark
import ml.alio.frame.common.type.remarkAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TypeTest {

    @Test
    fun `enum get remark`() {
        val remark = LetType::class.java.remark()
        Assertions.assertTrue(remark.field.size == Remark("让负类型", mapOf("LET_FLAT" to "让平",
                "LET_WIN" to "让胜",
                "LET_NEGATIVE" to "让负")).field.size)
    }

    @Test
    fun `enum get remark all`() {
        val remark = LetType::class.java.remarkAll()
        println(remark)
    }

    @Test
    fun `enum get remark f name param`() {
        val remark = LetType::class.java.remark("LET_FLAT")
        Assertions.assertEquals(remark, "让平")
    }

    @Test
    fun `enum get remark f name`() {
        Assertions.assertEquals(LetType.LET_FLAT.remark, "让平")
    }
}
