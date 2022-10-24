package net.javadog.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.javadog.chat.mapper.MsgUnreadRecordMapper;
import net.javadog.chat.model.entity.MsgUnreadRecord;
import net.javadog.chat.service.MsgUnreadRecordService;
import org.springframework.stereotype.Service;

/**
 * @Description: 消息未读标识接口实现类
 * @author: hdx
 * @Date: 2022-08-25 09:33
 * @version: 1.0
 **/
@Service
public class MsgUnreadRecordServiceImpl extends ServiceImpl<MsgUnreadRecordMapper, MsgUnreadRecord> implements MsgUnreadRecordService {

    @Override
    public void update(MsgUnreadRecord msgUnreadRecord) {
        this.saveOrUpdate(msgUnreadRecord);
    }
}
