package net.javadog.chat.common.enums;

import lombok.Getter;
import net.javadog.chat.common.base.enums.AbstractBaseExceptionEnum;

/**
 * @Description: 业务枚举类
 * @Author: hdx
 * @Date: 2022/1/30 9:12
 * @Version: 1.0
 */
@Getter
public enum ServiceErrorEnum implements AbstractBaseExceptionEnum {
    // 数据操作错误定义
    CONFIRMATION_PASSWORD_ERROR(1001, "确认密码不一致!"),
    // 用户错误相关
    USER_IS_EXIT(2001, "用户已存在!"),
    USER_IS_NO_EXIT(2002, "用户不存在!"),
    USER_EMAIL_NO_match(2003, "用户名和邮箱不匹配!"),
    USER_OLD_PASSWORD_ERROR(2004, "用户老密码不正确!"),
    PASSWORD_IS_ERROR(2005, "用户密码错误!"),
    // 文件错误
    FILE_IS_NULL(3001, "文件不能为空!"),
    FILE_MAX_POST_SIZE(3002, "文件大小超出最大限制!"),
    FILE_UPLOAD_ERROR(3003, "文件上传失败!"),
    // 邀请错误
    INVITATION_ALREADY_ERROR(4001, "请耐心等待,请勿重复邀请!"),
    FRIEND_ALREADY_ERROR(4002, "你们已经是好友，请勿重新操作!"),
    // 消息错误
    MSG_REVOKE_TIMEOUT_ERROR(5001, "发送已超3分钟,无法撤回!"),
    ;

    /**
     * 错误码
     */
    private final Integer resultCode;

    /**
     * 错误描述
     */
    private final String resultMsg;

    ServiceErrorEnum(Integer resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
