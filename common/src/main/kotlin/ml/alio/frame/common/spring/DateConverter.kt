package ml.alio.frame.common.spring

import ml.alio.frame.common.log
import ml.alio.frame.common.parseDate
import org.springframework.core.convert.converter.Converter
import java.util.*

/**
 * 字符串与时间类型解析器
 */
class DateConverter(private vararg val format: String) : Converter<String, Date> {
    override fun convert(source: String?): Date? {
        source?.let {
            try {
                format.forEach { f ->
                    return f.parseDate(it)
                }
            } catch (e: Exception) {
                log.debug(e.message)
            }
        }
        return null
    }
}
