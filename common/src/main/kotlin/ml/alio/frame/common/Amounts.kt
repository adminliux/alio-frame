package ml.alio.frame.common

import java.math.BigDecimal
import java.text.DecimalFormat

fun Double.rounding(size: Int = 2) = BigDecimal(this).setScale(size, BigDecimal.ROUND_HALF_UP).toDouble()
fun Float.rounding(size: Int = 2) = this.toDouble().rounding(size).toFloat()
fun Float.roundingRMB() = this.rounding(2)

/**
 * 金额精确到分单位小树形态转换为长整型
 */
val Float.long: Long
    get() = DecimalFormat("00.00").format(this.roundingRMB()).replace(".", "").toLong()


