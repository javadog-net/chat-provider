package net.javadog.chat.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.javadog.chat.common.base.vo.BaseVo;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Description: 好友信息Vo
 * @author: hdx
 * @Date: 2022-06-14 10:46
 * @version: 1.0
 **/
@Data
@ApiModel("好友信息Vo")
public class FriendMsgVo extends BaseVo {

    @ApiModelProperty(value = "消息内容")
    @NotBlank(message = "消息内容不能为空")
    private String msgContent;

    @ApiModelProperty(value = "消息类型(0-系统消息;1-文字;2-图片;3-语音;4-视频)")
    @NotBlank(message = "消息类型不能为空")
    private byte msgType;

    @ApiModelProperty(value = "消息发送方")
    @NotBlank(message = "消息发送方不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fromUserId;

    @ApiModelProperty(value = "消息接收方")
    @NotBlank(message = "消息接收方不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long toUserId;

    @ApiModelProperty(value = "状态(0-未读;1-已读;2-撤回;3-删除)")
    private byte status;

    @ApiModelProperty(value = "消息来源(0-好友;1-群组)")
    private byte source;

    @ApiModelProperty(value = "语音时长")
    private String time;

}
