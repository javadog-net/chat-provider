package net.javadog.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.javadog.chat.model.entity.MsgUnreadRecord;

/**
 * @Description: 消息未读标识接口
 * @Author: hdx
 * @Date: 2022/1/13 16:27
 * @Version: 1.0
 */
public interface MsgUnreadRecordService extends IService<MsgUnreadRecord> {

    /**
     * 消息标识更新
     *
     * @param msgUnreadRecord
     * @return
     */
    void update(MsgUnreadRecord msgUnreadRecord);
}
