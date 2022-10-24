package net.javadog.chat.common.enums;

import lombok.Getter;

/**
 * @author: hdx
 * @Date: 2022-08-08 16:03
 * @version: 1.0
 **/
@Getter
public enum InvitationStatusEnum {

    // 数据定义
    WAIT((byte) 0, "等待同意!"),
    PASS((byte) 1, "已同意!"),
    REFUSE((byte) 2, "已拒绝!");

    /**
     * 对应码
     */
    private final Byte resultCode;

    /**
     * 对应描述
     */
    private final String resultMsg;

    InvitationStatusEnum(Byte resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
