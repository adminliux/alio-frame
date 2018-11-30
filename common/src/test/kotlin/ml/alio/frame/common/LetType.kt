package ml.alio.frame.common

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("让负类型")
    enum class LetType(val code: Int? = null) {
        @ApiModelProperty("让平")
        LET_FLAT(1),
        @ApiModelProperty("让胜")
        LET_WIN(1),
        @ApiModelProperty("让负")
        LET_NEGATIVE
    }
