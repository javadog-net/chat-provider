package net.javadog.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.javadog.chat.model.entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 好友信息mapper接口
 * @Author: hdx
 * @Date: 2022/1/13 16:26
 * @Version: 1.0
 */
public interface MessageMapper extends BaseMapper<Message> {
    List<Message> list(@Param("message") Message message);
}
