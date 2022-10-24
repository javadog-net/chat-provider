package net.javadog.chat.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.javadog.chat.common.base.entity.BaseEntity;

import java.util.Date;

/**
 * @Description: 朋友消息和群组消息汇总-消息列表
 * @author: hdx
 * @Date: 2022-07-18 14:24
 * @version: 1.0
 **/
@Data
public class Message extends BaseEntity {

    /**
     * 消息来源(0-好友;1-群组)
     */
    private byte source;

    /**
     * 消息类型(0-系统消息;1-文字;2-图片;3-语音;4-视频)
     */
    private byte msgType;

    /**
     * 目标放Id
     */
    private Long targetId;

    /**
     * 关联方Id
     */
    private Long mineUserId;

    /**
     * 消息目标方名称(朋友昵称/群组群名)
     */
    private String targetName;

    /**
     * 消息发送方群人员名称
     */
    private String groupUserName;

    /**
     * 消息目标方头像(朋友头像/群组图)
     */
    private String targetAvatar;

    /**
     * 最后内容
     */
    private String lastContent;

    /**
     * 目的方用户id
     */
    private String toUserId;

    /**
     * 发送方方用户id
     */
    private String fromUserId;

    /**
     * 最后发送时间
     */
    private Date lastTime;

    /**
     * 昵称，用于群组中展示发送人
     */
    private String nickname;

    /**
     * 未读数量
     */
    private Integer unread;

    /**
     * 状态(0-未读;1-已读;2-撤回)
     */
    private byte status;

    /**
     * 语音时长
     */
    private String time;
}
