package net.javadog.chat.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.javadog.chat.common.base.vo.BaseVo;


/**
 * @Description: 好友邀请Vo
 * @Author: hdx
 * @Date: 2022/1/30 11:22
 * @Version: 1.0
 */
@Data
@ApiModel("好友邀请Vo")
public class InvitationVo extends BaseVo {

    @ApiModelProperty(value = "用户Id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @ApiModelProperty(value = "好友Id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long friendId;

    @ApiModelProperty(value = "用户昵称")
    private String userNickname;

    @ApiModelProperty(value = "好友昵称")
    private String friendNickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "好友头像")
    private String friendAvatar;

    @ApiModelProperty(value = "用户头像")
    private String userAvatar;

    @ApiModelProperty(value = "状态")
    private byte status;

    @ApiModelProperty(value = "拒绝理由")
    private String reason;

}
