package ml.alio.frame.common.ms

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/***
 * @author lizhun
 * control层和service层交互bean类
 */
@ApiModel(value = "固定返回结果", description = "固定返回结果")
open class Result : java.io.Serializable {

    /**
     * 返回码
     */
    @ApiModelProperty(value = "返回码", name = "mark", example = "0")
    open var mark = "-1"

    /**
     * 提示语
     */
    @ApiModelProperty(value = "提示语", name = "tip", example = "成功")
    var tip = "json数据错误"

}