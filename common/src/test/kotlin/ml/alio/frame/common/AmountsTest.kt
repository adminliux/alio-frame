package ml.alio.frame.common

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AmountsTest {
    @Test
    fun rounding() {
        Assertions.assertEquals(11.111.rounding(2), 11.11)
        Assertions.assertEquals(11.116.rounding(2), 11.12)
        Assertions.assertEquals(11.116.toFloat().rounding(2), 11.12.toFloat())
        Assertions.assertEquals(11.111.toFloat().rounding(2), 11.11.toFloat())
    }

    @Test
    fun roundingRMB() {
        Assertions.assertEquals(11.111.toFloat().roundingRMB(), 11.11.toFloat())
        Assertions.assertEquals(11.116.toFloat().roundingRMB(), 11.12.toFloat())
    }

    @Test
    fun long() {
        Assertions.assertEquals(100.86.toFloat().long, 10086)
        Assertions.assertEquals(100.866.toFloat().long, 10087)
        Assertions.assertEquals(100.864.toFloat().long, 10086)

        Assertions.assertEquals(100.8.toFloat().long, 10080)
    }
}