package ml.alio.frame.common

import java.util.*


fun random(min: Int = 1, max: Int = 10, size: Int = 1): List<Int> {
    val random = Random()
    var listOf = listOf<Int>()
    for (i in 0 until size) {
        listOf = listOf.plus(random.nextInt(max) + min)
    }
    return listOf
}

fun randomMerge(min: Int = 1, max: Int = 10, size: Int = 1): String {
    var str = ""
    random(min, max, size).forEach { str += it }
    return str
}

fun randomMergeInt(min: Int = 1, max: Int = 10, size: Int = 1) = randomMerge(min, max, size).toInt()

fun <T, V> List<T>.each(body: () -> V) {
    this.each(body)
}