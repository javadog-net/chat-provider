package net.javadog.chat.model.entity;

import lombok.Data;
import net.javadog.chat.common.base.entity.BaseEntity;

/**
 * @Description: 群组消息
 * @author: hdx
 * @Date: 2022-06-14 10:46
 * @version: 1.0
 **/
@Data
public class GroupMsg extends BaseEntity {

    /**
     * 群组id
     */
    private Long groupId;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 消息类型(0-系统消息;1-文字;2-图片;3-语音;4-视频)
     */
    private byte msgType;

    /**
     * 发送方Id
     */
    private Long fromUserId;

    /**
     * 发送方昵称
     */
    private String fromUserNickname;

    /**
     * 发送方头像
     */
    private String fromUserAvatar;

    /**
     * 语音时长
     */
    private String time;

    /**
     * 状态 1：正常；0：删除
     */
    private byte status;

}
