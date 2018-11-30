package ml.alio.frame.common.spring

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.context.config.annotation.RefreshScope

/**
 * web配置集合
 */
@RefreshScope
@ConfigurationProperties(prefix = "ml.common")
class WebConfig {
    /**
     * 时间格式化类型
     */
    var dateFormats = arrayOf("yyyy-MM-dd HH:mm:ss")
}
