package net.javadog.chat.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 好友/群组信息Vo
 * @author: hdx
 * @Date: 2022-06-14 10:46
 * @version: 1.0
 **/
@Data
@ApiModel("好友/群组信息Vo")
public class MessageVo implements Serializable {

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
    @JsonSerialize(using = ToStringSerializer.class)
    private Long targetId;

    /**
     * 关联方Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long mineUserId;

    /**
     * 消息目标方名称(朋友昵称/群组群名)
     */
    private String targetName;

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
    private Long unread;

    /**
     * 状态(0-未读;1-已读;2-撤回)
     */
    private byte status;
}
