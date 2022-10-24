package net.javadog.chat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.javadog.chat.model.entity.Group;
import net.javadog.chat.model.vo.GroupUserJoinVo;
import net.javadog.chat.model.vo.GroupUserVo;
import net.javadog.chat.model.vo.GroupVo;
import net.javadog.chat.model.vo.MsgHandleVo;

/**
 * @Description: 群组接口
 * @Author: hdx
 * @Date: 2022/1/13 16:27
 * @Version: 1.0
 */
public interface GroupService extends IService<Group> {

    /**
     * 群组保存
     *
     * @param groupVo
     * @return net.javadog.chat.model.vo.GroupVo
     */
    GroupVo add(GroupVo groupVo);

    /**
     * 群组分页列表
     *
     * @param groupUserVo
     * @param current
     * @param size
     * @return net.javadog.chat.model.vo.GroupVo
     */
    IPage<GroupVo> page(GroupUserVo groupUserVo, int current, int size);

    /**
     * 群组信息
     *
     * @param groupId
     * @return net.javadog.chat.model.vo.GroupVo
     */
    GroupVo detail(String groupId);

    /**
     * 拉人入群
     *
     * @param groupUserJoinVo
     */
    void pull(GroupUserJoinVo groupUserJoinVo);

    /**
     * 加入群组
     *
     * @param groupUserJoinVo
     */
    void join(GroupUserJoinVo groupUserJoinVo);

    /**
     * 好友消息撤回
     *
     * @param msgHandleVo
     */
    void msgHandle(MsgHandleVo msgHandleVo);
}
