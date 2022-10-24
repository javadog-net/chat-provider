package net.javadog.chat.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.javadog.chat.common.base.response.ResponseData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 用户控制器
 * @Author: hdx
 * @Date: 2022/1/13 16:39
 * @Version: 1.0
 */

@RestController
@Api(tags = "登出控制器")
@RequestMapping("/logout")
public class LogoutController {

    @ApiOperation(value = "用户登出", notes = "登出")
    @GetMapping
    public ResponseData logout() {
        //在这里执行退出系统前需要清空的数据
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return ResponseData.success("退出登录");
    }
}
