package ml.alio.frame.common

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*


fun Date.addSecond(value: Int) = this.add(Calendar.SECOND, value)
fun Date.add(unit: Int, value: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(unit, value)
    return calendar.time
}


fun Date.differenceSecond(time: Date) = (this.time - time.time) / 1000
fun Date.differentDays(tow: Date) = Dates.differentDays(this, tow)

const val gmtFormat = "yyyy-MM-dd'T'HH:mm:ss'.'sssZ"
val gmtTimeZone = TimeZone.getTimeZone("GMT")

fun Date.format(fmt: String, timeZone: TimeZone? = null): String {
    val sdf = SimpleDateFormat(fmt)
    timeZone?.let { sdf.timeZone = it }
    return sdf.format(this)
}

val Date.cDate: Any?
    get() {
        val f = Date::class.java.getDeclaredField("cdate")
        f.isAccessible = true
        return f.get(this)
    }

val Date.cDateStr
    get() = this.cDate?.toString()

fun Date.formatGmt(timeZone: TimeZone? = null) = this.format(gmtFormat, timeZone)

fun String.parseDate(date: String, timeZone: TimeZone? = null): Date {
    val sdf = SimpleDateFormat(this)
    timeZone?.let { sdf.timeZone = it }
    val pos = ParsePosition(0)
    return sdf.parse(date, pos)
}

fun String.parseDateGmt(timeZone: TimeZone? = null) = gmtFormat.parseDate(this, timeZone)

/**
 * 判断日期[this]是否在[startDate]和[endDate]之间
 */
fun Date.belongCalendar(startDate: Date, endDate: Date): Boolean {
    val now = Calendar.getInstance()
    now.time = this
    val start = Calendar.getInstance()
    start.time = startDate
    val end = Calendar.getInstance()
    end.time = endDate
    return now.after(start) && now.before(end)
}

/**
 * 判断日期格式化内[this]是否在[startDate]和[endDate]之间
 */
fun Date.belongCalendarFormat(startDate: Date, endDate: Date, format: String): Boolean {
    val startDateParse = format.parseDate(startDate.format(format))
    val endDateParse = format.parseDate(endDate.format(format))
    return format.parseDate(this.format(format)).belongCalendar(startDateParse, endDateParse)
}

/**
 * 判断时间(不含日期)内[this]是否在[startDate]和[endDate]之间
 */
fun Date.belongCalendarTime(startDate: Date, endDate: Date) = this.belongCalendarFormat(startDate, endDate, "HH:mm:ss")
