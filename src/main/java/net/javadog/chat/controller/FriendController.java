package net.javadog.chat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.javadog.chat.common.base.response.ResponseData;
import net.javadog.chat.model.vo.FriendVo;
import net.javadog.chat.service.FriendService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Description: 好友控制器
 * @Author: hdx
 * @Date: 2022/1/13 16:39
 * @Version: 1.0
 */

@RestController
@Api(tags = "好友控制器")
@RequestMapping("/friend")
public class FriendController {

    @Resource
    private FriendService friendService;

    @ApiOperation(value = "好友保存", notes = "好友--新增")
    @PostMapping
    public ResponseData add(final @RequestBody @Valid FriendVo friendVo) {
        FriendVo resFriendVo = friendService.add(friendVo);
        return ResponseData.success(resFriendVo);
    }

    @ApiOperation(value = "好友列表", notes = "好友--获取列表")
    @GetMapping
    public ResponseData get(final FriendVo friendVo,
                            final @RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                            final @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        IPage<FriendVo> page = friendService.page(friendVo, current, size);
        return ResponseData.success(page);
    }

    @ApiOperation(value = "好友详情", notes = "好友--获取详情")
    @GetMapping("/{friendId}")
    public ResponseData detail(@PathVariable String friendId) {
        FriendVo friendVo = friendService.detail(friendId);
        return ResponseData.success(friendVo);
    }

    @ApiOperation(value = "好友更新", notes = "好友--更新")
    @PutMapping
    public ResponseData update(final @RequestBody @Valid FriendVo friendVo) {
        if (friendService.update(friendVo)) {
            return ResponseData.success();
        }
        return ResponseData.error("更新失败");
    }

    @ApiOperation(value = "好友删除", notes = "好友--删除")
    @DeleteMapping("/{id}")
    public ResponseData delete(@PathVariable String id) {
        if (friendService.delete(id)) {
            return ResponseData.success();
        }
        return ResponseData.error("删除失败");
    }
}
