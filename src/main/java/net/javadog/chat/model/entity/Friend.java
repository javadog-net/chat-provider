package net.javadog.chat.model.entity;

import lombok.Data;
import net.javadog.chat.common.base.entity.BaseEntity;

/**
 * @Description: 好友实体
 * @author: hdx
 * @Date: 2022-06-14 10:46
 * @version: 1.0
 **/
@Data
public class Friend extends BaseEntity {

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 好友Id
     */
    private Long friendId;

    /**
     * 好友昵称
     */
    private String nickname;

    /**
     * 好友头像
     */
    private String avatar;

    /**
     * 好友昵称首字母
     */
    private String alphabetic;

    /**
     * 状态 1：正常；0：删除
     */
    private byte status;

}
