package ml.alio.frame.common.mybatis

import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

open class BaseEntity(
        /**
         * 主键
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        /**
         * 记录创建时间
         */
        var gmtDatetime: Date? = null,
        /**
         * 记录修改时间
         */
        var uptDatetime: Date? = null) : java.io.Serializable