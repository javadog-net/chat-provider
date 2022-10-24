package net.javadog.chat.model.entity;

import lombok.Data;
import net.javadog.chat.common.base.entity.BaseEntity;

/**
 * @Description: 群组用户
 * @author: hdx
 * @Date: 2022-06-14 10:46
 * @version: 1.0
 **/
@Data
public class GroupUser extends BaseEntity {

    /**
     * 群组id
     */
    private Long groupId;

    /**
     * 群组名称
     */
    private String groupName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String userNickname;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 是否管理员(0-否；1-是)
     */
    private byte adminable;

    /**
     * 来源(0-创建入群；1-扫码入群；2.拉人入群)
     */
    private byte source;

    /**
     * 状态 1：正常；0：删除； -1退群
     */
    private byte status;

}
