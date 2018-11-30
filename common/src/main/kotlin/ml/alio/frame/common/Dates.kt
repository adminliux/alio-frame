package ml.alio.frame.common

import java.util.*


object Dates {
    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    fun differentDays(date1: Date, date2: Date): Int {
        val cal1 = Calendar.getInstance()
        cal1.time = date1

        val cal2 = Calendar.getInstance()
        cal2.time = date2
        val day1 = cal1.get(Calendar.DAY_OF_YEAR)
        val day2 = cal2.get(Calendar.DAY_OF_YEAR)

        val year1 = cal1.get(Calendar.YEAR)
        val year2 = cal2.get(Calendar.YEAR)
        if (year1 != year2)
        //同一年
        {
            var timeDistance = 0
            for (i in year1 until year2) {
                timeDistance += if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)
                //闰年
                {
                    366
                } else
                //不是闰年
                {
                    365
                }
            }

            return timeDistance + (day2 - day1)
        } else
        //不同年
        {
            return day2 - day1
        }
    }

    fun calLastedTime(startDate: Date, endDate: Date = Date()): Int {
        val a = endDate.time
        val b = startDate.time
        return ((a - b) / 1000).toInt()
    }
}