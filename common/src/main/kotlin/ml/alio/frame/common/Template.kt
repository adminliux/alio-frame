package ml.alio.frame.common

import org.apache.velocity.VelocityContext
import org.apache.velocity.app.VelocityEngine
import java.io.StringWriter


fun <T> velocityCompile(content: String, data: T): String {
    val tempFile = tempFile(suffix = "temp")
    try {
        tempFile.writeText(content)
        val ve = VelocityEngine()
        ve.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, tempFile.parent)
        ve.init()
        val t = ve.getTemplate(tempFile.name)
        val ctx = VelocityContext()
        ctx.put("_", data)
        val sw = StringWriter()
        t.merge(ctx, sw)
        return sw.toString()
    } finally {
        tempFile.delete()
    }
}

