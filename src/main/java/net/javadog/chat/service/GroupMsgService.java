package net.javadog.chat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.javadog.chat.model.entity.GroupMsg;
import net.javadog.chat.model.vo.GroupMsgVo;

/**
 * @Description: 群组消息接口
 * @Author: hdx
 * @Date: 2022/1/13 16:27
 * @Version: 1.0
 */
public interface GroupMsgService extends IService<GroupMsg> {

    /**
     * 好友消息保存
     *
     * @param groupMsgVo
     * @return net.javadog.chat.model.vo.GroupMsgVo
     */
    GroupMsgVo add(GroupMsgVo groupMsgVo);

    /**
     * 群组消息分页列表
     *
     * @param groupMsgVo
     * @param current
     * @param size
     * @return net.javadog.chat.model.vo.IPage<GroupMsgVo>
     */
    IPage<GroupMsgVo> page(GroupMsgVo groupMsgVo, Integer current, Integer size);

}
