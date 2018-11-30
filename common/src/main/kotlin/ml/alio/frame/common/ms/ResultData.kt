package ml.alio.frame.common.ms

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.apache.log4j.Logger
import org.springframework.transaction.interceptor.TransactionAspectSupport

@ApiModel(value = "响应结果", description = "固定响应结果返回结果")
open class ResultData<T> : Result() {
    @ApiModelProperty(value = "响应数据", name = "data", example = "{}")
    var data: T? = null
    private val logger = Logger.getLogger(ResultData::class.java)

    override//事务回滚
    var mark: String
        get() = super.mark
        set(mark) {
            setRollbackOnly(mark)
            super.mark = mark
        }

    fun setRollbackOnly(mark: String = this.mark) {
        if (mark != "0") {
            try {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
            } catch (e: Exception) {
                logger.debug(e.message)
            }
        }
    }
}


fun ok(tip: String): ResultData<*> {
    return ok<Any>(tip, null)
}

fun <T> okData(data: T): ResultData<T> {
    return ok("成功", data)
}

fun <T> fast(mark: String, data: T?, tip: String): ResultData<T> {
    val resultData = ResultData<T>()
    resultData.tip = tip
    resultData.data = data
    resultData.mark = mark
    return resultData
}

fun fast(tip: String, mark: String): ResultData<*> {
    return fast<Any>(tip, null, mark)
}

fun <T> ok(tip: String, data: T?): ResultData<T> {
    return fast("0", data, tip)
}

fun <T> ok(data: T): ResultData<T> {
    return ok("成功", data)
}
