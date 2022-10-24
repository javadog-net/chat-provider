package net.javadog.chat.model.entity;

import lombok.Data;
import net.javadog.chat.common.base.entity.BaseEntity;

/**
 * @Description: 单聊好友信息实体
 * @author: hdx
 * @Date: 2022-06-14 10:46
 * @version: 1.0
 **/
@Data
public class FriendMsg extends BaseEntity {

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 消息类型(0-系统消息;1-文字;2-图片;3-语音;4-视频)
     */
    private byte msgType;

    /**
     * 消息发送方
     */
    private Long fromUserId;

    /**
     * 消息接收方
     */
    private Long toUserId;

    /**
     * 语音时长
     */
    private String time;

    /**
     * 状态(0-未读;1-已读;2-撤回;3-删除)
     */
    private byte status;
}
