package ml.alio.frame.common.web

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

/**
 * 把response和request存入当前线程容器中
 *
 * @author 刘兴
 */
object ServletThreadLocal {
    private val requestLocal = ThreadLocal<HttpServletRequest>()
    private val responseLocal = ThreadLocal<HttpServletResponse>()

    var request: HttpServletRequest?
        get() = requestLocal.get()
        set(request) = requestLocal.set(request)

    var response: HttpServletResponse?
        get() = responseLocal.get()
        set(response) = responseLocal.set(response)

    val session: HttpSession
        get() = requestLocal.get().session

    /**
     * 获取JSONp参数
     *
     * @return 参数名
     */
    val callbackParam: String?
        get() {
            var callback: String? = null
            if (request != null) {
                callback = request!!.getParameter("callback")
            }
            return callback
        }
}