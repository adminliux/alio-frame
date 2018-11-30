package ml.alio.frame.common

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectOutputStream
import java.io.OutputStream
import java.math.BigInteger


open class PairStr : Pair<String, String>()

val <T> T.byteArray: ByteArray
    get() {
        val bytes: ByteArray?
        val bos = ByteArrayOutputStream()
        try {
            val oos = ObjectOutputStream(bos as OutputStream?)
            oos.writeObject(this)
            oos.flush()
            bytes = bos.toByteArray()
            oos.close()
            bos.close()
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
        return bytes
    }

fun String.hex(size: Int = 16) = BigInteger(this, size).toInt()
fun String.hex16() = this.hex(16)


fun <T, V> List<T>.eachVal(body: (t: T) -> V): List<V> {
    var ls = listOf<V>()
    this.forEach {
        body(it)?.let { ls = ls.plus(it) }
    }
    return ls
}


fun <T, V> Array<T>.eachVal(body: (t: T) -> V) = this.toList().eachVal(body)

inline val <reified T> T.log: Log
    get() = LogFactory.getLog(T::class.java)

fun a() {

}

inline fun <reified T> T.toJsonKeyString() =
        JSONObject.toJavaObject(JSON.parseObject(Gson().toJson(this)), Map::class.java).toKeyString()


fun <T, V> Map<T, V>.toKeyString(): Map<String, Any> {
    var paramMap = mapOf<String, Any>()
    this.forEach { t, u ->
        if (u != null) paramMap = paramMap.plus(t.toString() to u as Any)
    }
    return paramMap
}

inline fun <reified T> Gson.fromJson(json: String): T {
    val jsonType = object : TypeToken<T>() {
    }.type
    return this.fromJson<T>(json, jsonType)
}



fun <K, V> List<kotlin.Pair<K, V?>>.toMap(): Map<K, V?> {
    var map = mapOf<K, V?>()
    this.forEach { k ->
        map = map.plus(k.first to k.second)
    }
    return map
}
