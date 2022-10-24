package net.javadog.chat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.javadog.chat.model.entity.FriendMsg;
import net.javadog.chat.model.vo.FriendMsgVo;
import net.javadog.chat.model.vo.MsgHandleVo;

/**
 * @Description: 好友消息接口
 * @Author: hdx
 * @Date: 2022/1/13 16:27
 * @Version: 1.0
 */
public interface FriendMsgService extends IService<FriendMsg> {

    /**
     * 好友消息保存
     *
     * @param friendMsgVo
     * @return net.javadog.chat.model.vo.FriendMsgVo
     */
    FriendMsgVo add(FriendMsgVo friendMsgVo);

    /**
     * 好友消息分页列表
     *
     * @param friendMsgVo
     * @param current
     * @param size
     * @return net.javadog.chat.model.vo.IPage<FriendMsgVo>
     */
    IPage<FriendMsgVo> page(FriendMsgVo friendMsgVo, Integer current, Integer size);

    /**
     * 好友消息撤回
     *
     * @param msgHandleVo
     */
    void msgHandle(MsgHandleVo msgHandleVo);
}
