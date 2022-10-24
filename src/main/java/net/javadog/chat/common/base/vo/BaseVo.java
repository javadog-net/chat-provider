package net.javadog.chat.common.base.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 基础共通实体
 * @Author: hdx
 * @Date: 2022/2/7 15:50
 * @Version: 1.0
 */

@Accessors(chain = true)
@ApiModel("基础共通实体Vo")
@Data
public class BaseVo implements Serializable {

    private static final long serialVersionUID = 787081245464173581L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 是否删除 0-否; 1-是
     */
    @ApiModelProperty(value = "是否删除")
    private Boolean deleted;

    /**
     * 创建人0
     */
    @ApiModelProperty("创建人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
