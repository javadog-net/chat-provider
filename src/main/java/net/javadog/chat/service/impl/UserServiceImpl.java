package net.javadog.chat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.javadog.chat.common.enums.CommonStatusEnum;
import net.javadog.chat.common.enums.ServiceErrorEnum;
import net.javadog.chat.common.exception.ServiceException;
import net.javadog.chat.common.shiro.util.JwtUtil;
import net.javadog.chat.common.shiro.util.SubjectUtil;
import net.javadog.chat.common.util.EncryptUtil;
import net.javadog.chat.common.util.NickNameUtil;
import net.javadog.chat.mapper.UserMapper;
import net.javadog.chat.model.entity.User;
import net.javadog.chat.model.vo.*;
import net.javadog.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

/**
 * @Description: 用户接口实现类
 * @Author: hdx
 * @Date: 2022/1/13 16:32
 * @Version: 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Override
    public Boolean register(RegisterVo registerVo) {
        // 校验是否已存在此手机号
        User one = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, registerVo.getUsername()));
        // 判断用户是否存在
        if (null != one) {
            // 已存在用户,抛出异常
            throw new ServiceException(ServiceErrorEnum.USER_IS_EXIT);
        }
        // vo->entity
        User user = new User();
        BeanUtil.copyProperties(registerVo, user);
        // 密码加密
        String password = EncryptUtil.encrypt(user.getPassword());
        user.setPassword(password);
        // 昵称随机生成
        user.setNickname(NickNameUtil.getName());
        // 头像随机生成 https://api.multiavatar.com/xxx.png
        String avatar = "http://api.multiavatar.com/" + (int) (Math.random() * 100) + ".png";
        user.setAvatar(avatar);
        // 默认男
        user.setSex("男");
        // 简介
        user.setIntro("那一吻,吻定了我们的情,但也吻散了我们的缘");
        return this.save(user);
    }

    @Override
    public UserVo login(LoginVo loginVo) {
        User one = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, loginVo.getUsername()).eq(User::getStatus, CommonStatusEnum.YES.getResultCode()));
        // 判断用户是否存在
        if (null == one) {
            // 不存在
            throw new ServiceException(ServiceErrorEnum.USER_IS_NO_EXIT);
        }
        // 判断密码
        if (!loginVo.getPassword().equals(EncryptUtil.decrypt(one.getPassword()))) {
            // 密码错误
            throw new ServiceException(ServiceErrorEnum.PASSWORD_IS_ERROR);
        }
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(one, userVo);
        // 放入token
        userVo.setToken(JwtUtil.createToken(loginVo.getUsername()));
        return userVo;
    }

    @Override
    public Boolean update(UserVo userVo) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper();
        updateWrapper.set(User::getAvatar, userVo.getAvatar())
                .set(User::getSex, userVo.getSex())
                .set(User::getNickname, userVo.getNickname())
                .set(User::getBirthday, userVo.getBirthday())
                .set(User::getIntro, userVo.getIntro())
                .set(User::getEmail, userVo.getEmail())
                .eq(User::getId, userVo.getId());
        return this.update(updateWrapper);
    }

    @Override
    public Boolean retrieve(RetrieveVo retrieveVo) {
        String username = retrieveVo.getUsername();
        String email = retrieveVo.getEmail();
        LambdaUpdateWrapper<User> queryWrapper = new LambdaUpdateWrapper();
        queryWrapper.eq(User::getUsername, username).eq(User::getEmail, email);
        User user = this.getOne(queryWrapper);
        if (ObjectUtil.isNull(user)) {
            throw new ServiceException(ServiceErrorEnum.USER_EMAIL_NO_match);
        }
        // 重置密码
        String pwd = IdUtil.simpleUUID().substring(0, 6);
        // 密码加密
        String password = EncryptUtil.encrypt(pwd);
        user.setPassword(password);
        if (this.saveOrUpdate(user)) {
            // 发送邮件
            this.sendMail(email, pwd);
        }
        return true;
    }

    @Override
    public Boolean pwd(UserPwdVo userPwdVo) {
        Long userId = SubjectUtil.getUserId();
        User user = this.getById(userId);
        if (ObjectUtil.isNull(user)) {
            throw new ServiceException(ServiceErrorEnum.USER_IS_NO_EXIT);
        }
        // 校验老密码
        String password = EncryptUtil.encrypt(userPwdVo.getOldPwd());
        if (!StrUtil.equals(password, user.getPassword())) {
            throw new ServiceException(ServiceErrorEnum.USER_OLD_PASSWORD_ERROR);
        }
        // 新密码
        String newPwd = EncryptUtil.encrypt(userPwdVo.getNewPwd());
        user.setPassword(newPwd);
        return this.saveOrUpdate(user);
    }

    @Override
    public IPage<UserVo> page(UserVo userVo, int current, int size) {
        IPage<User> page = new Page<>(current, size);
        Long userId = SubjectUtil.getUserId();
        LambdaQueryWrapper<User> lambdaQuery = new LambdaQueryWrapper();
        lambdaQuery.like(User::getUsername, userVo.getUsername()).eq(User::getStatus, CommonStatusEnum.YES.getResultCode()).ne(User::getUsername, SubjectUtil.getUsername());
        IPage<User> users = this.page(page, lambdaQuery);
        // IPage<entity>->IPage<vo>
        IPage<UserVo> convert = users.convert(user -> {
            UserVo resUserVo = new UserVo();
            BeanUtil.copyProperties(user, resUserVo);
            return resUserVo;
        });
        return convert;
    }

    private void sendMail(String email, String password) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //主题
        mailMessage.setSubject("JavaDog Chat密码找回");
        //内容
        mailMessage.setText("您的密码重置为:" + password);
        mailMessage.setTo(email);
        mailMessage.setFrom("postmaster@javadog.net");
        javaMailSender.send(mailMessage);
    }

}
