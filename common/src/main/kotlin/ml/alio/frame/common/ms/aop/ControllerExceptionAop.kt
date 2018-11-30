package ml.alio.frame.common.ms.aop

import com.google.gson.Gson
import ml.alio.frame.common.ms.aop.exception.ControllerException
import ml.alio.frame.common.ms.fast
import ml.alio.frame.common.web.ServletThreadLocal
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.PrintWriter

/**
 * 控制层抛出异常处理组件Aspect切面Bean
 *
 * @author 刘兴
 */
@Aspect
@Component
class ControllerExceptionAop {
    private val log = LoggerFactory.getLogger(javaClass)

    @AfterThrowing(value = "execution(* *..controller..*Controller.*(..))", throwing = "ex")
    fun afterThrowingAdvice(joinPoint: JoinPoint, ex: Exception) {
        val resultData = if (ex is ControllerException) {
            log.debug(ex.message)
            ex.resp
        } else {
            log.error("ERROR", ex)
            fast(ex.message ?: "服务器错误", "-1")
        }

        resultData.setRollbackOnly()

        log.info("joinPoint", *joinPoint.args)
        val response = ServletThreadLocal.response!!
        response.setHeader("Content-type", "application/json;charset=UTF-8")
        //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
        response.characterEncoding = "UTF-8"
        val writer: PrintWriter
        try {
            writer = response.writer
            writer.append(Gson().toJson(resultData))
            writer.close()
        } catch (e: IOException) {
            log.error("获取PrintWriter失败", e)
        }
    }
}
