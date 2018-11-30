package ml.alio.frame.common.ms.aop.exception

import ml.alio.frame.common.ms.ResultData
import ml.alio.frame.common.ms.fast


class ControllerException : RuntimeException {
    var resp = fast("-1", "出错了")

    constructor(message: String, code: Int) {
        resp = fast(message, code.toString() + "")
    }

    constructor(res: ResultData<*>) {
        this.resp = res
    }
}
