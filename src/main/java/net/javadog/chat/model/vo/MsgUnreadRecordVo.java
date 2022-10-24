package net.javadog.chat.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import net.javadog.chat.common.base.entity.BaseEntity;

/**
 * @Description: 未读消息表
 * @author: hdx
 * @Date: 2022-09-06 11:27
 * @version: 1.0
 **/
@Data
@Accessors(chain = true)
@ApiModel("未读消息实体")
public class MsgUnreadRecordVo extends BaseEntity {

    @ApiModelProperty(value = "目标方Id(对应好友/群组 id)")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long targetId;

    @ApiModelProperty(value = "用户Id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @ApiModelProperty(value = "未读数量")
    private int unreadNum;

}
