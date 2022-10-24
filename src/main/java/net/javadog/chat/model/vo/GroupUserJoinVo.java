package net.javadog.chat.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.javadog.chat.common.base.entity.BaseEntity;

/**
 * @Description: 群组用户拉入Vo
 * @author: hdx
 * @Date: 2022-06-14 10:46
 * @version: 1.0
 **/
@Data
public class GroupUserJoinVo extends BaseEntity {

    @ApiModelProperty(value = "群组id")
    private Long groupId;

    @ApiModelProperty(value = "用户ids(逗号隔开)")
    private String userIds;
}
