package net.javadog.chat.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.javadog.chat.common.base.vo.BaseVo;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Description: 消息处理Vo
 * @author: hdx
 * @Date: 2022-06-14 10:46
 * @version: 1.0
 **/
@Data
@ApiModel("消息处理Vo")
public class MsgHandleVo extends BaseVo {

    @ApiModelProperty(value = "处理类型(1-撤销;1-删除")
    @NotBlank(message = "消息类型不能为空")
    private byte type;

    @ApiModelProperty(value = "消息内容")
    private MessageVo message;
}
