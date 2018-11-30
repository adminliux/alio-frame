package ml.alio.frame.common

import org.apache.commons.lang.StringUtils

object StringUtil {

    private fun isNullOrBlack(str: String?): Boolean {
        return "" == str || null == str
    }

    /**
     * Checks if none of the CharSequences are blank ("") or null and whitespace
     * only.. StringUtils.isNoneBlank(null) = false
     * StringUtils.isNoneBlank(null, "foo") = false
     * StringUtils.isNoneBlank(null, null) = false StringUtils.isNoneBlank("",
     * "bar") = false StringUtils.isNoneBlank("bob", "") = false
     * StringUtils.isNoneBlank("  bob  ", null) = false StringUtils.isNoneBlank(
     * " ", "bar") = false StringUtils.isNoneBlank("foo", "bar") = true
     * StringUtils.isNoneBlank([]) = false
     *
     */
    fun isBlank(vararg string: String): Boolean {
        if (string.isEmpty()) {
            return true
        }
        for (str in string) {
            if (StringUtils.isBlank(str)) {
                return true
            }
        }
        return false
    }

    /**
     * 判断字符串是否相等
     *
     * @param string
     * 字符
     * @return 是返回true
     */
    fun equals(vararg string: String): Boolean {
        if (string.isNotEmpty()) {
            for (i in string.indices) {
                val str = string[i]
                if (i < string.size - 1) {
                    if (!StringUtils.equals(str, string[i + 1])) {
                        return false
                    }
                }
            }
        }
        return true
    }

    /**
     * 判断字符串是否不相等
     *
     * @param string
     * 字符
     * @return 是返回true
     */
    fun equalsNo(vararg string: String): Boolean {
        if (string.isNotEmpty()) {
            for (i in string.indices) {
                val str = string[i]
                if (i < string.size - 1) {
                    if (StringUtils.equals(str, string[i + 1])) {
                        return false
                    }
                }
            }
        }
        return true
    }
}

