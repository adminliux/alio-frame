package ml.alio.frame.common

/**
 * K-V反转
 */
fun <K, V> Map<K, V>.reversal(): Map<V, K> {
    var map = mapOf<V, K>()
    this.forEach { t, u -> map = map.plus(u to t) }
    return map
}