package ml.alio.frame.common

import org.junit.jupiter.api.Test

class MapTest {
    @Test
    fun toKeyString() {
        val toKeyString = Pair(1, Pair(2, null)).toJsonKeyString()
        println(toKeyString)
    }
}
