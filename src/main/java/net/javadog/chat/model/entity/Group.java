package net.javadog.chat.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.javadog.chat.common.base.entity.BaseEntity;

/**
 * @Description: 群组实体
 * @author: hdx
 * @Date: 2022-06-14 10:46
 * @version: 1.0
 **/
@Data
@TableName("`group`")
public class Group extends BaseEntity {

    /**
     * 群名称
     */
    private String name;

    /**
     * 群头像
     */
    private String avatar;

    /**
     * 群公告
     */
    private String notice;

    /**
     * 群介绍
     */
    private String intro;

    /**
     * 组名称首字母
     */
    private String Alphabetic;

    /**
     * 群主
     */
    private Long adminUserId;

    /**
     * 状态 1：正常；0：删除
     */
    private byte status;

}
