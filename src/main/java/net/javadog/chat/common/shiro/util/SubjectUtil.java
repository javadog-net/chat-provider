package net.javadog.chat.common.shiro.util;

import lombok.extern.slf4j.Slf4j;
import net.javadog.chat.common.exception.ServiceException;
import net.javadog.chat.model.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;

/**
 * 获取Subject工具类
 *
 * @author: hdx
 * @Date: 2022-06-13 17:48
 * @version: 1.0
 **/
@Slf4j
public class SubjectUtil {

    /**
     * 获取用户信息
     */
    public static User getUserInfo() {
        Subject subject = null;
        try {
            subject = SecurityUtils.getSubject();
        } catch (UnavailableSecurityManagerException e) {
            log.info("身份鉴权异常,{}", e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
        return (User) subject.getPrincipal();
    }

    /**
     * 获取用户username
     */
    public static String getUsername() {
        return getUserInfo().getUsername();
    }

    /**
     * 获取用户头像
     */
    public static String getAvatar() {
        return getUserInfo().getAvatar();
    }

    /**
     * 获取用户Id
     */
    public static Long getUserId() {
        return getUserInfo().getId();
    }
}
