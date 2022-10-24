package net.javadog.chat.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.javadog.chat.common.base.vo.BaseVo;

/**
 * @Description: 好友Vo
 * @Author: hdx
 * @Date: 2022/1/30 11:22
 * @Version: 1.0
 */
@Data
@ApiModel("好友Vo")
public class FriendVo extends BaseVo {

    @ApiModelProperty(value = "用户Id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @ApiModelProperty(value = "好友Id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long friendId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "好友头像")
    private String avatar;

    @ApiModelProperty(value = "字母索引")
    private String alphabetic;

    @ApiModelProperty(value = "状态")
    private byte status;

}
