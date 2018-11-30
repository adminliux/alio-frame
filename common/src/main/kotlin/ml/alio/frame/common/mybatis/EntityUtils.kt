package ml.alio.frame.common.mybatis

import ml.alio.frame.common.StringUtil
import org.apache.commons.beanutils.PropertyUtilsBean
import org.apache.commons.lang.StringUtils
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.util.*
import java.util.regex.Pattern
import javax.persistence.Transient

/**
 * 实体类工具类
 *
 * @author 刘兴
 */
object EntityUtils {

    /**
     * 将javaBean转为map类型，然后返回一个map类型的值
     *
     * @param entity javaBean
     * @return Map格式
     */
    fun beanToMap(entity: Any): Map<String, String> {
        val params = HashMap<String, String>(0)
        try {
            val propertyUtilsBean = PropertyUtilsBean()
            val descriptors = propertyUtilsBean.getPropertyDescriptors(entity)
            for (i in descriptors.indices) {
                val name = descriptors[i].name
                if (!StringUtils.equals(name, "class")) {
                    params[name] = propertyUtilsBean.getNestedProperty(entity, name).toString()
                }
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        return params
    }

    /**
     * 获取实体类属性值与属性名
     *
     * @param t             实体类
     * @param isNamingHump  是否使用驼峰命名
     * @param isEmpty       是否将空字符串设置为null
     * @param excludeFields 排除字段
     * @return 键值对
     */
    fun getEntityAttrMap(t: Any, isEmpty: Boolean, isNamingHump: Boolean,
                         vararg excludeFields: String): Map<String, Any?> {
        val map = HashMap<String, Any?>()
        val clazz = t.javaClass
        getObjectAttr(t, clazz, map, isEmpty, isNamingHump, *excludeFields)

        val superclass = clazz.superclass
        if (superclass != null) {
            getObjectAttr(t, superclass, map, isEmpty, isNamingHump, *excludeFields)
        }

        return map
    }

    /**
     * 获取对象属性不包含父类
     *
     * @param t             对象
     * @param clazz         class
     * @param map           内容
     * @param isNamingHump  是否使用驼峰命名
     * @param isEmpty       是否将空字符串设置为null
     * @param excludeFields 排除字段
     * @return 键值对 EX:{kCardCode=啊哈哈, kCardName=null}
     */
    fun getObjectAttr(t: Any, clazz: Class<out Any>, map: MutableMap<String, Any?>,
                      isEmpty: Boolean, isNamingHump: Boolean, vararg excludeFields: String): Map<String, Any?> {
        val declaredMethods = clazz.declaredMethods
        for (method in declaredMethods) {
            var isBreak = false
            var isExclude = false
            var name = method.name

            if (name.startsWith("get")) {
                name = name.substring(3)
                name = Character.toLowerCase(name[0]) + name.substring(1)
                if (!StringUtil.isBlank(*excludeFields)) {
                    for (excludeField in excludeFields) {
                        if (name == excludeField) {
                            isExclude = true
                            break
                        }
                    }
                }
                if (isExclude) {
                    break
                }
                var declaredField: Field?
                try {
                    declaredField = clazz.getDeclaredField(name)
                } catch (e: NoSuchFieldException) {
                    throw RuntimeException(e)
                } catch (e: SecurityException) {
                    throw RuntimeException(e)
                }

                if (declaredField == null) break
                val annotations = declaredField.annotations
                if (annotations != null) {
                    for (annotation in annotations) {
                        if (annotation.annotationClass == Transient::class.java) {
                            isBreak = true
                            break
                        }

                    }
                }
                if (!isBreak) {
                    try {
                        var invoke: Any? = method.invoke(t)
                        if (isEmpty) {
                            val type = declaredField.type
                            if (type == String::class.java) {
                                val str = invoke as String?
                                if (str == "") {
                                    invoke = null
                                }
                            }
                        }
                        if (isNamingHump) {
                            name = javaToNamingHump(name)
                        }
                        map[name] = invoke
                    } catch (e: IllegalAccessException) {
                        throw RuntimeException(e)
                    } catch (e: IllegalArgumentException) {
                        throw RuntimeException(e)
                    } catch (e: InvocationTargetException) {
                        throw RuntimeException(e)
                    }

                }
            }

        }
        return map
    }

    /**
     * java命名规范转驼峰命名 <br></br>
     * eg:bankCardCode = bank_card_code
     *
     * @param string java字段命名
     * @return 驼峰命名
     */
     fun javaToNamingHump(string: String): String {
        val regexStr = "[A-Z]"
        val matcher = Pattern.compile(regexStr).matcher(string)
        val sb = StringBuffer()
        while (matcher.find()) {
            val g = matcher.group()
            matcher.appendReplacement(sb, "_" + g.toLowerCase())
        }
        matcher.appendTail(sb)
        if (sb[0] == '_') {
            sb.delete(0, 1)
        }
        return sb.toString()

    }

    /**
     * 检测实体类属性是否为空
     *
     * @param model  实体
     * @param fields 取消的字段
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @Throws(NoSuchMethodException::class, SecurityException::class, IllegalAccessException::class, IllegalArgumentException::class, InvocationTargetException::class)
    @JvmOverloads
    fun isBlank(model: Any, vararg fields: String = arrayOf()): Boolean {
        return isBlank(model, true, *fields)
    }

    /**
     * 检测实体类属性是否为空
     *
     * @param model  实体
     * @param fields 取消的字段
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @Throws(NoSuchMethodException::class, SecurityException::class, IllegalAccessException::class, IllegalArgumentException::class, InvocationTargetException::class)
    fun isBlank(model: Any, stringBlank: Boolean, vararg fields: String): Boolean {
        val cls = model.javaClass
        val declaredMethods = cls.declaredMethods
        for (method in declaredMethods) {
            var nameMethod = method.name
            if (nameMethod.startsWith("get")) {
                nameMethod = nameMethod.substring(3, nameMethod.length)
                nameMethod = Character.toLowerCase(nameMethod.substring(0).toCharArray()[0]) + nameMethod.substring(1, nameMethod.length)
                var isMethodBreak = false
                for (field in fields) {
                    if (nameMethod == field) {
                        isMethodBreak = true
                        break
                    }
                }
                if (isMethodBreak) {
                    break
                }
                val resutl = method.invoke(model)
                if (stringBlank) {
                    if (resutl is String) {
                        return if (StringUtils.isBlank(resutl)) {
                            false
                        } else {
                            break
                        }
                    }
                } else {
                    if (resutl == null) {
                        return false
                    }
                }
            }
        }
        return true
    }


    /**
     * 如果有字段ID的话跳过他
     *
     * @param t 实体对象
     * @return 是否为空
     */
    fun isNullById(t: Any): Boolean {
        val isNull: Boolean
        try {
            isNull = isBlank(t, "id")
        } catch (e: NoSuchMethodException) {
            throw RuntimeException(e)
        } catch (e: SecurityException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        } catch (e: IllegalArgumentException) {
            throw RuntimeException(e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException(e)
        }

        return isNull
    }
}
