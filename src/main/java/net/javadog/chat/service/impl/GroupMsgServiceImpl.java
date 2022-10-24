package net.javadog.chat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.javadog.chat.common.enums.CommonStatusEnum;
import net.javadog.chat.common.shiro.util.SubjectUtil;
import net.javadog.chat.mapper.GroupMsgMapper;
import net.javadog.chat.model.entity.GroupMsg;
import net.javadog.chat.model.entity.GroupUser;
import net.javadog.chat.model.entity.MsgUnreadRecord;
import net.javadog.chat.model.vo.GroupMsgVo;
import net.javadog.chat.service.GroupMsgService;
import net.javadog.chat.service.GroupUserService;
import net.javadog.chat.service.MsgUnreadRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 群组消息接口实现类
 * @author: hdx
 * @Date: 2022-08-25 09:33
 * @version: 1.0
 **/
@Service
public class GroupMsgServiceImpl extends ServiceImpl<GroupMsgMapper, GroupMsg> implements GroupMsgService {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Resource
    private MsgUnreadRecordService msgUnreadRecordService;

    @Resource
    private GroupUserService groupUserService;

    @Override
    public GroupMsgVo add(GroupMsgVo groupMsgVo) {
        // 设置群组成员未读
        LambdaQueryWrapper<GroupUser> groupUserWrapper = new LambdaQueryWrapper<>();
        groupUserWrapper.eq(GroupUser::getGroupId, groupMsgVo.getGroupId());
        groupUserService.list(groupUserWrapper).forEach(groupUser -> {
            if (NumberUtil.equals(groupMsgVo.getFromUserId(), groupUser.getUserId())) {
                return;
            }
            // 查询未读数量
            LambdaQueryWrapper<MsgUnreadRecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MsgUnreadRecord::getUserId, groupUser.getUserId()).eq(MsgUnreadRecord::getTargetId, groupMsgVo.getGroupId());
            MsgUnreadRecord msgUnreadRecord = msgUnreadRecordService.getOne(queryWrapper);
            if (ObjectUtil.isNull(msgUnreadRecord)) {
                msgUnreadRecord = new MsgUnreadRecord();
                msgUnreadRecord.setUnreadNum(1);
                msgUnreadRecord.setUserId(groupUser.getUserId());
                msgUnreadRecord.setTargetId(groupMsgVo.getGroupId());
                msgUnreadRecord.setSource(CommonStatusEnum.MSG_SOURCE_GROUP.getResultCode());
            } else {
                msgUnreadRecord.setUnreadNum(msgUnreadRecord.getUnreadNum() + 1);
            }
            // 处理未读数量
            msgUnreadRecordService.update(msgUnreadRecord);
        });
        // vo->entity
        GroupMsg groupMsg = new GroupMsg();
        BeanUtil.copyProperties(groupMsgVo, groupMsg);
        if (baseMapper.insert(groupMsg) > 0) {
            groupMsgVo.setId(groupMsg.getId());
            groupMsgVo.setSource(CommonStatusEnum.MSG_SOURCE_GROUP.getResultCode());
            messagingTemplate.convertAndSend("/topic/message/" + groupMsg.getGroupId(), JSONUtil.toJsonStr(groupMsgVo));
        }
        // entity->vo
        GroupMsgVo resGroupMsgVo = new GroupMsgVo();
        BeanUtil.copyProperties(groupMsg, resGroupMsgVo);
        return resGroupMsgVo;
    }

    @Override
    public IPage<GroupMsgVo> page(GroupMsgVo groupMsgVo, Integer current, Integer size) {
        IPage<GroupMsg> page = new Page<>(current, size);
        LambdaQueryWrapper<GroupMsg> query = new LambdaQueryWrapper<>();
        query.eq(GroupMsg::getGroupId, groupMsgVo.getGroupId());
        query.orderByDesc(GroupMsg::getCreateTime);
        IPage<GroupMsg> groupMsgs = this.page(page, query);
        // IPage<entity>->IPage<vo>
        IPage<GroupMsgVo> convert = groupMsgs.convert(GroupMsg -> BeanUtil.copyProperties(GroupMsg, GroupMsgVo.class));
        // 查询未读数量
        LambdaQueryWrapper<MsgUnreadRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MsgUnreadRecord::getUserId, SubjectUtil.getUserId()).eq(MsgUnreadRecord::getTargetId, groupMsgVo.getGroupId());
        MsgUnreadRecord msgUnreadRecord = msgUnreadRecordService.getOne(queryWrapper);
        if (ObjectUtil.isNotNull(msgUnreadRecord)) {
            msgUnreadRecord.setUnreadNum(0);
            msgUnreadRecordService.update(msgUnreadRecord);
        }
        return convert;
    }

}
