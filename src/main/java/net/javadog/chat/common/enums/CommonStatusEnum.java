package net.javadog.chat.common.enums;

import lombok.Getter;

/**
 * @author: hdx
 * @Date: 2022-08-08 16:03
 * @version: 1.0
 **/
@Getter
public enum CommonStatusEnum {
    // 是否
    NO((byte) 0, "否"),
    YES((byte) 1, "是"),

    // 邀请状态
    INVITATION_WAIT((byte) 0, "待通过"),
    INVITATION_PASS((byte) 1, "通过"),
    INVITATION_REFUSE((byte) 2, "拒绝"),

    // 消息类型
    MSG_TYPE_SYSTEM((byte) 0, "system"),
    MSG_TYPE_TEXT((byte) 1, "text"),
    MSG_TYPE_IMAGE((byte) 2, "image"),
    MSG_TYPE_VOICE((byte) 3, "voice"),
    MSG_TYPE_VIDEO((byte) 4, "video"),

    // 消息状态(0-未读;1-已读;2-撤回;3-删除)
    MSG_STATUS_NORMAL((byte) 0, "正常"),
    MSG_STATUS_REVOKE((byte) 1, "撤回"),
    MSG_STATUS_DELETE((byte) 2, "删除"),

    // 群组来源(0-创建入群；1-扫码入群；2.拉人入群)
    GROUP_USER_CREATE((byte) 0, "创建入群"),
    GROUP_USER_SCAN((byte) 1, "扫码入群"),
    GROUP_USER_PULL((byte) 2, "拉人入群"),

    // 消息来源(0-好友；1-群组)
    MSG_SOURCE_FRIEND((byte) 0, "好友"),
    MSG_SOURCE_GROUP((byte) 1, "群组"),
    ;

    /**
     * 对应码
     */
    private final Byte resultCode;

    /**
     * 对应描述
     */
    private final String resultMsg;

    CommonStatusEnum(Byte resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
