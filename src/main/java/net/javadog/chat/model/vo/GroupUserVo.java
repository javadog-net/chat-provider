package net.javadog.chat.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.javadog.chat.common.base.entity.BaseEntity;

/**
 * @Description: 群组用户Vo
 * @author: hdx
 * @Date: 2022-06-14 10:46
 * @version: 1.0
 **/
@Data
public class GroupUserVo extends BaseEntity {

    @ApiModelProperty(value = "群组id")
    private Long groupId;

    @ApiModelProperty(value = "群组名称")
    private String groupName;

    @ApiModelProperty(value = "用户id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @ApiModelProperty(value = "用户昵称")
    private String userNickname;

    @ApiModelProperty(value = "用户头像")
    private String userAvatar;

}
