package net.javadog.chat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.javadog.chat.common.shiro.util.SubjectUtil;
import net.javadog.chat.mapper.MessageMapper;
import net.javadog.chat.model.entity.Message;
import net.javadog.chat.model.entity.User;
import net.javadog.chat.model.vo.MessageVo;
import net.javadog.chat.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 好友信息接口实现类
 * @Author: hdx
 * @Date: 2022/1/13 16:32
 * @Version: 1.0
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    public List<MessageVo> list(MessageVo messageVo) {
        User userInfo = SubjectUtil.getUserInfo();
        // vo->entity
        Message message = new Message();
        BeanUtil.copyProperties(messageVo, message);
        message.setMineUserId(userInfo.getId());
        List<Message> messages = baseMapper.list(message);
        List<MessageVo> messagesVo = new ArrayList<>();
        // entity->vo
        messages.forEach(item -> {
            MessageVo msgVo = new MessageVo();
            BeanUtil.copyProperties(item, msgVo);
            messagesVo.add(msgVo);
        });
        return messagesVo;
    }
}
