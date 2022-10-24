package net.javadog.chat.model.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import net.javadog.chat.common.base.entity.BaseEntity;

/**
 * @Description: 未读消息表
 * @author: hdx
 * @Date: 2022-09-06 11:27
 * @version: 1.0
 **/
@Data
@Accessors(chain = true)
@ApiModel("未读消息实体")
public class MsgUnreadRecord extends BaseEntity {

    /**
     * 目标方Id(对应好友/群组 id)
     */
    private Long targetId;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 未读数量
     */
    private int unreadNum;

    /**
     * 消息来源(0-好友;1-群组)
     */
    private byte source;

}
