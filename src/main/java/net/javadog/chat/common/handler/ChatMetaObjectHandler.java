package net.javadog.chat.common.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import net.javadog.chat.common.shiro.util.SubjectUtil;
import net.javadog.chat.model.entity.User;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @Description: MybatisPlus属性自动填充配置
 * @author: hdx
 * @Date: 2022-06-16 16:39
 * @version: 1.0
 **/
@Slf4j
@Component
public class ChatMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        User userInfo = SubjectUtil.getUserInfo();
        if (ObjectUtil.isNotNull(userInfo)) {
            this.setFieldValByName("createBy", userInfo.getId(), metaObject);
            this.setFieldValByName("updateBy", userInfo.getId(), metaObject);
        }
        this.setFieldValByName("createTime", DateUtil.date(), metaObject);
        this.setFieldValByName("updateTime", DateUtil.date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        User userInfo = SubjectUtil.getUserInfo();
        if (ObjectUtil.isNotNull(userInfo)) {
            this.setFieldValByName("updateBy", userInfo.getId(), metaObject);
        }
        this.setFieldValByName("updateTime", DateUtil.date(), metaObject);
    }
}
