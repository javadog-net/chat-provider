package net.javadog.chat.common.enums;

import lombok.Getter;
import net.javadog.chat.common.base.enums.AbstractBaseExceptionEnum;

/**
 * @Description: HttpCode枚举类
 * @Author: hdx
 * @Date: 2022/1/21 16:20
 * @Version: 1.0
 */
@Getter
public enum CommonErrorEnum implements AbstractBaseExceptionEnum {
    // 数据操作错误定义
    SAVE_ERROR(9000, "保存失败!"),
    UPDATE_ERROR(9001, "更新失败!"),
    FIND_ERROR(9002, "查询失败!"),
    DELETE_ERROR(9003, "删除失败!");

    /**
     * 错误码
     */
    private final Integer resultCode;

    /**
     * 错误描述
     */
    private final String resultMsg;

    CommonErrorEnum(Integer resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

}