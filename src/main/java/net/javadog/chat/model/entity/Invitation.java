package net.javadog.chat.model.entity;

import lombok.Data;
import net.javadog.chat.common.base.entity.BaseEntity;

/**
 * @Description: 好友邀请表
 * @author: hdx
 * @Date: 2022-08-05 15:11
 * @version: 1.0
 **/
@Data
public class Invitation extends BaseEntity {

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 好友Id
     */
    private Long friendId;

    /**
     * 用户昵称
     */
    private String userNickname;

    /**
     * 好友昵称
     */
    private String friendNickname;

    /**
     * 好友头像
     */
    private String friendAvatar;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 状态(0-待通过; 1-已通过; 2-拒绝)
     */
    private byte status;

    /**
     * 拒绝理由
     */
    private String reason;
}
