package ml.alio.frame.common

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.util.*

class DateTest {
    @Test
    fun `date time format by gmt`() {
        val str = "2018-11-13T04:30:32.032Z"
        val parseDate = "2018-11-13T04:30:32.032Z".parseDateGmt()
        Assertions.assertEquals(str, parseDate.formatGmt())
    }

    @Test
    fun `now time format by gmt`() {
        val formatGmt = Date().formatGmt()
        val parseDateGmt = formatGmt.parseDateGmt()
        println(formatGmt)
        println(formatGmt)
    }

    @Test
    fun `belong calendar time`() {
        val format = "yyyy-MM-dd hh:mm:ss"
        val simpleDateFormat = SimpleDateFormat(format)
        val parse = simpleDateFormat.parse("2018-07-01 03:00:00")
        val start = simpleDateFormat.parse("2018-06-01 24:00:00")
        val end = simpleDateFormat.parse("2018-01-02 08:00:00")

        Assertions.assertEquals(parse.belongCalendarTime(start, end), true)
        Assertions.assertEquals(simpleDateFormat.parse("2018-02-01 13:00:00")
                .belongCalendarTime(start, end), false)
    }
}