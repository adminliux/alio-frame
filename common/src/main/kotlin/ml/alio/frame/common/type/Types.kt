package ml.alio.frame.common.type

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

fun <T> Class<T>.remark(): Remark {
    val remark = Remark()
    val model = this.getAnnotation(ApiModel::class.java)
    model?.let {
        remark.name = it.value
    }
    //字段
    this.declaredFields?.filter { it.name != "\$VALUE" }?.forEach {
        it.getAnnotation(ApiModelProperty::class.java)?.let { p ->
            remark.field = remark.field.plus(it.name to p.value)
        }
    }
    return remark
}

fun <T> Class<T>.remarkAll(): RemarkALl {
    val remark = RemarkALl()
    val model = this.getAnnotation(ApiModel::class.java)
    model?.let {
        remark.name = it.value
    }
    //字段
//    this.declaredFields?.filter { it.name != "\$VALUE" }?.forEach {
//        it.getAnnotation(ApiModelProperty::class.java)?.let { p ->
//            remark.field = remark.field.plus(it.name to p.value)
//        }
//    }
    return remark
}


fun <T> Class<T>.remark(fieldName: String): String? {
    var r: String? = null
    remark().field.forEach { t, u ->
        if (fieldName == t) {
            r = u
            return@forEach
        }
    }
    return r
}

val Enum<*>.remark: String?
    get() {
        return javaClass.remark(this.name)
    }
