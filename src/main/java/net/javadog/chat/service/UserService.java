package net.javadog.chat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.javadog.chat.model.entity.User;
import net.javadog.chat.model.vo.*;

/**
 * @Description: 用户接口类
 * @Author: hdx
 * @Date: 2022/1/13 16:27
 * @Version: 1.0
 */

public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param registerVo
     * @return java.lang.Boolean
     */
    Boolean register(RegisterVo registerVo);

    /**
     * 用户登录
     *
     * @param loginVo
     * @return net.javadog.chat.model.vo.UserVo
     */
    UserVo login(LoginVo loginVo);

    /**
     * 用户登录
     *
     * @param userVo
     * @return net.javadog.chat.model.vo.UserVo
     */
    Boolean update(UserVo userVo);

    /**
     * 用户密码找回
     *
     * @param retrieveVo
     * @return ResponseData
     */
    Boolean retrieve(RetrieveVo retrieveVo);

    /**
     * 用户密码修改
     *
     * @param userPwdVo
     * @return ResponseData
     */
    Boolean pwd(UserPwdVo userPwdVo);

    /**
     * 用户分页列表
     *
     * @param userVo
     * @param current
     * @param size
     * @return net.javadog.chat.model.vo.UserVo
     */
    IPage<UserVo> page(UserVo userVo, int current, int size);

}
