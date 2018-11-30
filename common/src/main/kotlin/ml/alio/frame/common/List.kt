package ml.alio.frame.common

import java.io.File
import java.util.*

fun <T> List<T>.randomElement() = this[randomMergeInt(0, this.size)]

fun List<Int>.min() = this.sortedWith(Comparator { v1, v2 -> v1 - v2 })[0]
fun List<Float>.min() = this.sortedWith(Comparator { v1, v2 -> (v1 - v2).toInt() })[0]


fun tempDir(prefix: String = UUID.randomUUID().toString(), suffix: String? = null, directory: File? = null) = createTempDir(prefix, suffix, directory)
fun tempFile(prefix: String = UUID.randomUUID().toString(), suffix: String? = null, directory: File? = null) = createTempFile(prefix, ".$suffix", directory)