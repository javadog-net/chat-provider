package net.javadog.chat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.javadog.chat.common.base.response.ResponseData;
import net.javadog.chat.model.vo.UserPwdVo;
import net.javadog.chat.model.vo.UserVo;
import net.javadog.chat.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Description: 用户控制器
 * @Author: hdx
 * @Date: 2022/1/13 16:39
 * @Version: 1.0
 */

@RestController
@Api(tags = "用户控制器")
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "用户详情", notes = "用户--详情")
    @GetMapping("/{userId}")
    public ResponseData detail(@PathVariable Long userId) {
        return ResponseData.success(userService.getById(userId));
    }

    @ApiOperation(value = "用户更新", notes = "用户--更新")
    @PutMapping
    public ResponseData update(final @RequestBody @Valid UserVo userVo) {
        if (userService.update(userVo)) {
            return ResponseData.success();
        }
        return ResponseData.error("更新失败");
    }

    @ApiOperation(value = "用户密码修改", notes = "用户--密码修改")
    @PutMapping("/pwd")
    public ResponseData pwd(final @RequestBody @Valid UserPwdVo userPwdVo) {
        if (userService.pwd(userPwdVo)) {
            return ResponseData.success();
        }
        return ResponseData.error("更新失败");
    }

    @ApiOperation(value = "用户列表", notes = "用户--获取列表")
    @GetMapping
    public ResponseData get(final UserVo UserVo,
                            final @RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                            final @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        IPage<UserVo> page = userService.page(UserVo, current, size);
        return ResponseData.success(page);
    }
}
