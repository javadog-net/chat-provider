package net.javadog.chat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.javadog.chat.common.enums.CommonStatusEnum;
import net.javadog.chat.common.enums.ServiceErrorEnum;
import net.javadog.chat.common.exception.ServiceException;
import net.javadog.chat.mapper.FriendMsgMapper;
import net.javadog.chat.model.entity.FriendMsg;
import net.javadog.chat.model.entity.MsgUnreadRecord;
import net.javadog.chat.model.vo.FriendMsgVo;
import net.javadog.chat.model.vo.MsgHandleVo;
import net.javadog.chat.service.FriendMsgService;
import net.javadog.chat.service.MsgUnreadRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 好友信息接口实现类
 * @Author: hdx
 * @Date: 2022/1/13 16:32
 * @Version: 1.0
 */
@Service
public class FriendMsgServiceImpl extends ServiceImpl<FriendMsgMapper, FriendMsg> implements FriendMsgService {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Resource
    private MsgUnreadRecordService msgUnreadRecordService;

    @Override
    public FriendMsgVo add(FriendMsgVo friendMsgVo) {
        // 查询未读数量
        LambdaQueryWrapper<MsgUnreadRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MsgUnreadRecord::getUserId, friendMsgVo.getToUserId()).eq(MsgUnreadRecord::getTargetId, friendMsgVo.getFromUserId());
        MsgUnreadRecord msgUnreadRecord = msgUnreadRecordService.getOne(queryWrapper);
        if (ObjectUtil.isNull(msgUnreadRecord)) {
            msgUnreadRecord = new MsgUnreadRecord();
            msgUnreadRecord.setUnreadNum(1);
            msgUnreadRecord.setUserId(friendMsgVo.getToUserId());
            msgUnreadRecord.setTargetId(friendMsgVo.getFromUserId());
            msgUnreadRecord.setSource(CommonStatusEnum.MSG_SOURCE_FRIEND.getResultCode());
        } else {
            msgUnreadRecord.setUnreadNum(msgUnreadRecord.getUnreadNum() + 1);
        }
        // 处理未读数量
        msgUnreadRecordService.update(msgUnreadRecord);
        // vo->entity
        FriendMsg friendMsg = new FriendMsg();
        BeanUtil.copyProperties(friendMsgVo, friendMsg);
        if (baseMapper.insert(friendMsg) > 0) {
            // 完善单聊VO参数
            friendMsgVo.setId(friendMsg.getId());
            friendMsgVo.setSource(CommonStatusEnum.MSG_SOURCE_FRIEND.getResultCode());
            messagingTemplate.convertAndSend("/simple/message/" + friendMsgVo.getToUserId(), JSONUtil.toJsonStr(friendMsgVo));
        }
        // entity->vo
        FriendMsgVo resFriendMsgVo = new FriendMsgVo();
        BeanUtil.copyProperties(friendMsg, resFriendMsgVo);
        return resFriendMsgVo;
    }

    @Override
    public IPage<FriendMsgVo> page(FriendMsgVo friendMsgVo, Integer current, Integer size) {
        IPage<FriendMsg> page = new Page<>(current, size);
        LambdaQueryWrapper<FriendMsg> query = new LambdaQueryWrapper<>();
        query.eq(FriendMsg::getFromUserId, friendMsgVo.getFromUserId()).eq(FriendMsg::getToUserId, friendMsgVo.getToUserId());
        query.or().eq(FriendMsg::getFromUserId, friendMsgVo.getToUserId()).eq(FriendMsg::getToUserId, friendMsgVo.getFromUserId());
        query.orderByDesc(FriendMsg::getCreateTime);
        IPage<FriendMsg> friendMsgs = this.page(page, query);
        // IPage<entity>->IPage<vo>
        IPage<FriendMsgVo> convert = friendMsgs.convert(FriendMsg -> BeanUtil.copyProperties(FriendMsg, FriendMsgVo.class));
        // 查询未读数量
        LambdaQueryWrapper<MsgUnreadRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MsgUnreadRecord::getUserId, friendMsgVo.getFromUserId()).eq(MsgUnreadRecord::getTargetId, friendMsgVo.getToUserId());
        MsgUnreadRecord msgUnreadRecord = msgUnreadRecordService.getOne(queryWrapper);
        if (ObjectUtil.isNotNull(msgUnreadRecord)) {
            msgUnreadRecord.setUnreadNum(0);
            msgUnreadRecordService.update(msgUnreadRecord);
        }
        return convert;
    }

    @Override
    public void msgHandle(MsgHandleVo msgHandleVo) {
        byte type = msgHandleVo.getType();
        Long msgId = msgHandleVo.getId();
        FriendMsg friendMsg = this.getById(msgId);
        if (CommonStatusEnum.MSG_STATUS_REVOKE.getResultCode() == type) {
            // 撤回
            DateTime dateTime = DateUtil.offsetMinute(friendMsg.getCreateTime(), 3);
            if (dateTime.getTime() < DateUtil.current()) {
                throw new ServiceException(ServiceErrorEnum.MSG_REVOKE_TIMEOUT_ERROR);
            }
            friendMsg.setStatus(CommonStatusEnum.MSG_STATUS_REVOKE.getResultCode());
            friendMsg.setMsgType(CommonStatusEnum.MSG_TYPE_SYSTEM.getResultCode());
        } else if (CommonStatusEnum.MSG_STATUS_DELETE.getResultCode() == type) {
            // 删除
            friendMsg.setStatus(CommonStatusEnum.MSG_STATUS_DELETE.getResultCode());
            friendMsg.setMsgType(CommonStatusEnum.MSG_TYPE_SYSTEM.getResultCode());
        }
        messagingTemplate.convertAndSend("/msgHandle/message/" + friendMsg.getToUserId(), JSONUtil.toJsonStr(msgHandleVo));
        this.updateById(friendMsg);
    }

}
