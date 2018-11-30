package ml.alio.frame.common.web

import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ServletThreadLocalInterceptor : HandlerInterceptor {

    override fun preHandle(httpServletRequest: HttpServletRequest?, httpServletResponse: HttpServletResponse?, o: Any?): Boolean {
        ServletThreadLocal.request = httpServletRequest
        ServletThreadLocal.response = httpServletResponse
        return true
    }

    @Throws(Exception::class)
    override fun postHandle(httpServletRequest: HttpServletRequest?, httpServletResponse: HttpServletResponse?, o: Any?, modelAndView: ModelAndView?) {
    }

    override fun afterCompletion(httpServletRequest: HttpServletRequest?, httpServletResponse: HttpServletResponse?, o: Any?, e: Exception?) {}
}  