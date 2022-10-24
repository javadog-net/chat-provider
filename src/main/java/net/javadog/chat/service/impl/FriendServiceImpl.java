package net.javadog.chat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.javadog.chat.common.enums.CommonStatusEnum;
import net.javadog.chat.common.shiro.util.SubjectUtil;
import net.javadog.chat.mapper.FriendMapper;
import net.javadog.chat.model.entity.Friend;
import net.javadog.chat.model.entity.User;
import net.javadog.chat.model.vo.FriendVo;
import net.javadog.chat.service.FriendService;
import org.springframework.stereotype.Service;

/**
 * @Description: 好友接口实现类
 * @Author: hdx
 * @Date: 2022/1/13 16:32
 * @Version: 1.0
 */
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {

    @Override
    public FriendVo add(FriendVo friendVo) {
        User userInfo = SubjectUtil.getUserInfo();
        // vo->entity
        Friend friend = new Friend();
        BeanUtil.copyProperties(friendVo, friend);
        friend.setUserId(userInfo.getId());
        friend.setStatus(CommonStatusEnum.YES.getResultCode());
        baseMapper.insert(friend);
        // entity->vo
        FriendVo resFriendVo = new FriendVo();
        BeanUtil.copyProperties(friend, resFriendVo);
        return resFriendVo;
    }

    @Override
    public IPage<FriendVo> page(FriendVo friendVo, Integer current, Integer size) {
        IPage<Friend> page = new Page<>(current, size);
        Friend friend = new Friend();
        BeanUtil.copyProperties(friendVo, friend);
        friend.setUserId(SubjectUtil.getUserId());
        QueryWrapper<Friend> friendQueryWrapper = new QueryWrapper<>();
        friendQueryWrapper.lambda().like(ObjectUtil.isNotNull(friend.getNickname()), Friend::getNickname, friend.getNickname()).eq(Friend::getUserId, SubjectUtil.getUserId()).eq(Friend::getStatus, CommonStatusEnum.YES.getResultCode());
        IPage<Friend> friends = this.page(page, friendQueryWrapper);
        // IPage<entity>->IPage<vo>
        IPage<FriendVo> convert = friends.convert(Friend -> BeanUtil.copyProperties(Friend, FriendVo.class));
        return convert;
    }

    @Override
    public boolean update(FriendVo friendVo) {
        Friend friend = new Friend();
        BeanUtil.copyProperties(friendVo, friend);
        return this.saveOrUpdate(friend);
    }

    @Override
    public FriendVo detail(String friendId) {
        Long userId = SubjectUtil.getUserId();
        LambdaUpdateWrapper<Friend> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Friend::getFriendId, friendId).eq(Friend::getUserId, userId).eq(Friend::getStatus, CommonStatusEnum.YES.getResultCode());
        Friend friend = baseMapper.selectOne(wrapper);
        FriendVo resFriendVo = new FriendVo();
        // entity->vo
        if (ObjectUtil.isNull(friend)) {
            return null;
        }
        BeanUtil.copyProperties(friend, resFriendVo);
        return resFriendVo;
    }

    @Override
    public boolean delete(String id) {
        Friend friend = this.getById(id);
        friend.setStatus(CommonStatusEnum.NO.getResultCode());
        return this.updateById(friend);
    }
}
