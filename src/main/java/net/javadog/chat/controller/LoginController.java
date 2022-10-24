package net.javadog.chat.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.javadog.chat.common.base.response.ResponseData;
import net.javadog.chat.model.vo.LoginVo;
import net.javadog.chat.model.vo.RetrieveVo;
import net.javadog.chat.model.vo.UserVo;
import net.javadog.chat.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Description: 用户控制器
 * @Author: hdx
 * @Date: 2022/1/13 16:39
 * @Version: 1.0
 */

@RestController
@Api(tags = "登录控制器")
@RequestMapping("/login")
public class LoginController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "用户登录", notes = "登录--不进行拦截")
    @PostMapping
    public ResponseData login(@RequestBody @Valid LoginVo loginVo) {
        UserVo userVo = userService.login(loginVo);
        return ResponseData.success(userVo);
    }

    @ApiOperation(value = "用户密码找回", notes = "登录--用户密码找回")
    @PostMapping("/retrieve")
    public ResponseData retrieve(@RequestBody @Valid RetrieveVo retrieveVo) {
        if (userService.retrieve(retrieveVo)) {
            return ResponseData.success();
        }
        return ResponseData.error("操作失败");
    }
}
