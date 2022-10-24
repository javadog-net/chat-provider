package net.javadog.chat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.javadog.chat.model.entity.Friend;
import net.javadog.chat.model.vo.FriendVo;

/**
 * @Description: 好友接口
 * @Author: hdx
 * @Date: 2022/1/13 16:27
 * @Version: 1.0
 */
public interface FriendService extends IService<Friend> {

    /**
     * 好友保存
     *
     * @param friendVo
     * @return net.javadog.chat.model.vo.FriendVo
     */
    FriendVo add(FriendVo friendVo);

    /**
     * 好友分页列表
     *
     * @param friendVo
     * @param current
     * @param size
     * @return net.javadog.chat.model.vo.IPage<FriendVo>
     */
    IPage<FriendVo> page(FriendVo friendVo, Integer current, Integer size);

    /**
     * 好友更新
     *
     * @param friendVo
     * @return boolean
     */
    boolean update(FriendVo friendVo);

    /**
     * 好友详情
     *
     * @param friendId
     * @return net.javadog.chat.model.vo.FriendVo
     */
    FriendVo detail(String friendId);

    /**
     * 好友删除
     *
     * @param id
     * @return boolean
     */
    boolean delete(String id);

}
