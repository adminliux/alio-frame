package ml.alio.frame.common

val <T> Class<T>.allAnnotation
    get() = this.declaredAnnotations.plus(this.annotations)

val <T> Class<T>.allFields
    get() = this.fields.plus(this.declaredFields)

val <T> Class<T>.allFieldsSuperclass
    get() = {
        val superclass = this.superclass
        val allFields = this.allFields
        if (superclass != Any::class.java)
            allFields.plus(superclass.allFields)
        else allFields
    }.invoke()

fun <K, V> List<ml.alio.frame.common.Pair<K, V?>>.toMap(): Map<K, V?> {
    var map = mapOf<K, V?>()
    this.forEach { k ->
        map = map.plus(k.key to k.value)
    }
    return map
}
