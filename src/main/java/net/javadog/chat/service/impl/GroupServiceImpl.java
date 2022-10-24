package net.javadog.chat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.javadog.chat.common.enums.CommonStatusEnum;
import net.javadog.chat.common.enums.ServiceErrorEnum;
import net.javadog.chat.common.exception.ServiceException;
import net.javadog.chat.common.shiro.util.SubjectUtil;
import net.javadog.chat.mapper.GroupMapper;
import net.javadog.chat.model.entity.Group;
import net.javadog.chat.model.entity.GroupMsg;
import net.javadog.chat.model.entity.GroupUser;
import net.javadog.chat.model.entity.User;
import net.javadog.chat.model.vo.GroupUserJoinVo;
import net.javadog.chat.model.vo.GroupUserVo;
import net.javadog.chat.model.vo.GroupVo;
import net.javadog.chat.model.vo.MsgHandleVo;
import net.javadog.chat.service.GroupMsgService;
import net.javadog.chat.service.GroupService;
import net.javadog.chat.service.GroupUserService;
import net.javadog.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 群组接口实现类
 * @author: hdx
 * @Date: 2022-08-25 09:33
 * @version: 1.0
 **/
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Resource
    private GroupUserService groupUserService;

    @Resource
    private GroupMsgService groupMsgService;

    @Resource
    private UserService userService;

    @Override
    public GroupVo add(GroupVo groupVo) {
        User userInfo = SubjectUtil.getUserInfo();
        // vo->entity
        Group group = new Group();
        BeanUtil.copyProperties(groupVo, group);
        group.setStatus(CommonStatusEnum.YES.getResultCode());
        group.setAdminUserId(userInfo.getId());
        String firstWord = StrUtil.sub(group.getName(), 0, 1);
        String alphabetic = PinyinUtil.getFirstLetter(firstWord, ",");
        group.setAlphabetic(alphabetic.toUpperCase());
        this.save(group);
        // 构建群组员
        this.buildGroupUser(group, userInfo);
        // 构建群消息
        this.buildGroupMsg(group, userInfo);
        // entity->vo
        GroupVo resGroupVo = new GroupVo();
        BeanUtil.copyProperties(group, resGroupVo);
        return resGroupVo;
    }

    @Override
    public IPage<GroupVo> page(GroupUserVo groupUserVo, int current, int size) {
        IPage<GroupUser> page = new Page<>(current, size);
        Long userId = SubjectUtil.getUserId();
        LambdaQueryWrapper<GroupUser> lambdaQuery = new LambdaQueryWrapper();
        lambdaQuery.eq(GroupUser::getUserId, userId).like(ObjectUtil.isNotNull(groupUserVo.getGroupName()), GroupUser::getGroupName, groupUserVo.getGroupName()).eq(GroupUser::getStatus, CommonStatusEnum.YES.getResultCode());
        IPage<GroupUser> groupUsers = groupUserService.page(page, lambdaQuery);
        // IPage<entity>->IPage<vo>
        IPage<GroupVo> convert = groupUsers.convert(groupUser -> {
            Group group = this.getById(groupUser.getGroupId());
            GroupVo groupVo = new GroupVo();
            BeanUtil.copyProperties(group, groupVo);
            return groupVo;
        });
        return convert;
    }

    @Override
    public GroupVo detail(String groupId) {
        Group group = this.getById(groupId);
        GroupVo groupVo = new GroupVo();
        BeanUtil.copyProperties(group, groupVo);
        LambdaQueryWrapper<GroupUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GroupUser::getGroupId, groupId);
        List<GroupUser> groupUsers = groupUserService.list(queryWrapper);
        List<GroupUserVo> resultGroupUsers = CollectionUtil.newArrayList();
        groupUsers.stream().forEach(item -> {
            GroupUserVo groupUserVo = new GroupUserVo();
            BeanUtil.copyProperties(item, groupUserVo);
            resultGroupUsers.add(groupUserVo);
        });
        groupVo.setGroupUsers(resultGroupUsers);
        return groupVo;
    }

    /**
     * @Description: 构建群组用户
     * @Param: dataInsulinPump
     * @return: void
     */
    private void buildGroupUser(Group group, User userInfo) {
        // 组员表中加入一条信息
        GroupUser groupUser = new GroupUser();
        // 群组id
        groupUser.setGroupId(group.getId());
        // 群名称
        groupUser.setGroupName(group.getName());
        // 群组员ID
        groupUser.setUserId(group.getAdminUserId());
        // 群组员昵称
        groupUser.setUserNickname(userInfo.getNickname() + "(群主)");
        // 群组员头像
        groupUser.setUserAvatar(userInfo.getAvatar());
        // 是否管理员
        groupUser.setAdminable(CommonStatusEnum.YES.getResultCode());
        // 状态 1：正常；0：删除； -1退群
        groupUser.setStatus(CommonStatusEnum.YES.getResultCode());
        groupUserService.save(groupUser);
    }

    /**
     * @Description: 构建群组消息
     * @Param: dataInsulinPump
     * @return: void
     */
    private void buildGroupMsg(Group group, User userInfo) {
        // 群组消息中加入一条信息
        GroupMsg groupMsg = new GroupMsg();
        groupMsg.setGroupId(group.getId());
        groupMsg.setMsgType(CommonStatusEnum.MSG_TYPE_SYSTEM.getResultCode());
        groupMsg.setMsgContent("欢迎" + userInfo.getNickname() + " ^_^");
        groupMsg.setStatus(CommonStatusEnum.MSG_STATUS_NORMAL.getResultCode());
        groupMsg.setFromUserId(userInfo.getId());
        groupMsg.setFromUserAvatar(userInfo.getAvatar());
        groupMsg.setFromUserNickname(userInfo.getNickname());
        groupMsgService.save(groupMsg);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pull(GroupUserJoinVo groupUserJoinVo) {
        String userIds = groupUserJoinVo.getUserIds();
        Long groupId = groupUserJoinVo.getGroupId();
        User userInfo = SubjectUtil.getUserInfo();
        List<String> listUserIds = Arrays.asList(userIds.split(","));
        Group group = this.getById(groupId);
        AtomicInteger num = new AtomicInteger();
        final String[] firstName = {""};
        listUserIds.forEach(userId -> {
            GroupUser newGroupUser = new GroupUser();
            User user = userService.getById(Long.parseLong(userId));
            // 如果已在群里则无需重复拉入
            LambdaQueryWrapper<GroupUser> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(GroupUser::getGroupId, groupId).eq(GroupUser::getUserId, userId);
            GroupUser groupUser = groupUserService.getOne(queryWrapper);
            if (ObjectUtil.isNull(groupUser)) {
                newGroupUser.setUserId(user.getId());
                newGroupUser.setGroupId(groupId);
                newGroupUser.setGroupName(group.getName());
                newGroupUser.setUserAvatar(user.getAvatar());
                newGroupUser.setUserNickname(user.getNickname());
                newGroupUser.setStatus(CommonStatusEnum.YES.getResultCode());
                newGroupUser.setAdminable(CommonStatusEnum.NO.getResultCode());
                newGroupUser.setSource(CommonStatusEnum.GROUP_USER_PULL.getResultCode());
                groupUserService.save(newGroupUser);
                num.getAndIncrement();
                if (StrUtil.isBlank(firstName[0])) {
                    firstName[0] = user.getNickname();
                }
            }
        });
        GroupMsg groupMsg = new GroupMsg();
        groupMsg.setGroupId(groupId);
        String tpl = "欢迎{},等{}人加入群聊^_^";
        groupMsg.setMsgContent(StrUtil.format(tpl, firstName, num));
        groupMsg.setMsgType(CommonStatusEnum.MSG_TYPE_SYSTEM.getResultCode());
        groupMsg.setFromUserId(userInfo.getId());
        groupMsg.setFromUserAvatar(userInfo.getAvatar());
        groupMsg.setFromUserNickname(userInfo.getNickname());
        groupMsg.setStatus(CommonStatusEnum.MSG_STATUS_NORMAL.getResultCode());
        groupMsgService.save(groupMsg);
    }

    @Override
    public void join(GroupUserJoinVo groupUserJoinVo) {
        String userId = groupUserJoinVo.getUserIds();
        Long groupId = groupUserJoinVo.getGroupId();
        Group group = this.getById(groupId);
        User user = userService.getById(Long.parseLong(userId));
        GroupUser newGroupUser = new GroupUser();
        newGroupUser.setUserId(Long.parseLong(userId));
        newGroupUser.setGroupId(groupId);
        newGroupUser.setGroupName(group.getName());
        newGroupUser.setUserAvatar(user.getAvatar());
        newGroupUser.setUserNickname(user.getNickname());
        newGroupUser.setStatus(CommonStatusEnum.YES.getResultCode());
        newGroupUser.setAdminable(CommonStatusEnum.NO.getResultCode());
        newGroupUser.setSource(CommonStatusEnum.GROUP_USER_SCAN.getResultCode());
        groupUserService.save(newGroupUser);
    }

    @Override
    public void msgHandle(MsgHandleVo msgHandleVo) {
        byte type = msgHandleVo.getType();
        Long msgId = msgHandleVo.getId();
        GroupMsg groupMsg = groupMsgService.getById(msgId);
        if (CommonStatusEnum.MSG_STATUS_REVOKE.getResultCode() == type) {
            // 撤回
            DateTime dateTime = DateUtil.offsetMinute(groupMsg.getCreateTime(), 3);
            if (dateTime.getTime() < DateUtil.current()) {
                throw new ServiceException(ServiceErrorEnum.MSG_REVOKE_TIMEOUT_ERROR);
            }
            groupMsg.setStatus(CommonStatusEnum.MSG_STATUS_REVOKE.getResultCode());
            groupMsg.setMsgType(CommonStatusEnum.MSG_TYPE_SYSTEM.getResultCode());
        } else if (CommonStatusEnum.MSG_STATUS_DELETE.getResultCode() == type) {
            // 删除
            groupMsg.setStatus(CommonStatusEnum.MSG_STATUS_DELETE.getResultCode());
            groupMsg.setMsgType(CommonStatusEnum.MSG_TYPE_SYSTEM.getResultCode());
        }
        groupMsgService.updateById(groupMsg);
        messagingTemplate.convertAndSend("/msgHandle/message/" + groupMsg.getGroupId(), JSONUtil.toJsonStr(msgHandleVo));
    }
}
