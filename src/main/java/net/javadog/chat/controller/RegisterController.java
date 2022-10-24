package net.javadog.chat.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.javadog.chat.common.base.response.ResponseData;
import net.javadog.chat.common.enums.ServiceErrorEnum;
import net.javadog.chat.common.exception.ServiceException;
import net.javadog.chat.model.vo.RegisterVo;
import net.javadog.chat.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Description: 注册控制器
 * @Author: hdx
 * @Date: 2022/1/29 14:57
 * @Version: 1.0
 */
@Api(tags = "注册控制器")
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "用户注册", notes = "用户自主注册")
    @PostMapping
    public ResponseData register(@RequestBody @Valid RegisterVo registerVo) {
        // 校验两次密码是否一致
        if (!registerVo.getPassword().equals(registerVo.getPasswordVerify())) {
            throw new ServiceException(ServiceErrorEnum.CONFIRMATION_PASSWORD_ERROR);
        }
        userService.register(registerVo);
        return ResponseData.success();
    }
}
