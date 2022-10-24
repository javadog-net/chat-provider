package net.javadog.chat.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.javadog.chat.common.base.entity.BaseEntity;

import java.util.List;

/**
 * @Description: 群组Vo
 * @author: hdx
 * @Date: 2022-06-14 10:46
 * @version: 1.0
 **/
@Data
public class GroupVo extends BaseEntity {

    @ApiModelProperty(value = "群名称")
    private String name;

    @ApiModelProperty(value = "群头像")
    private String avatar;

    @ApiModelProperty(value = "群公告")
    private String notice;

    @ApiModelProperty(value = "群介绍")
    private String intro;

    @ApiModelProperty(value = "组名称首字母")
    private String Alphabetic;

    @ApiModelProperty(value = "群主Id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long adminUserId;

    @ApiModelProperty(value = "状态 1：正常；0：删除")
    private byte status;

    @ApiModelProperty(value = "群组员")
    private List<GroupUserVo> groupUsers;

}
