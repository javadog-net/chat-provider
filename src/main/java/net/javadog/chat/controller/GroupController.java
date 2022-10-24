package net.javadog.chat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.javadog.chat.common.base.response.ResponseData;
import net.javadog.chat.model.vo.GroupUserJoinVo;
import net.javadog.chat.model.vo.GroupUserVo;
import net.javadog.chat.model.vo.GroupVo;
import net.javadog.chat.model.vo.MsgHandleVo;
import net.javadog.chat.service.GroupService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Description: 群组控制器
 * @Author: hdx
 * @Date: 2022/1/13 16:39
 * @Version: 1.0
 */

@RestController
@Api(tags = "群组控制器")
@RequestMapping("/group")
public class GroupController {

    @Resource
    private GroupService groupService;

    @ApiOperation(value = "群组保存", notes = "群组--新增")
    @PostMapping
    public ResponseData add(final @RequestBody @Valid GroupVo groupVo) {
        GroupVo resGroupVo = groupService.add(groupVo);
        return ResponseData.success(resGroupVo);
    }

    @ApiOperation(value = "群组列表", notes = "群组--获取列表")
    @GetMapping
    public ResponseData get(final GroupUserVo groupUserVo,
                            final @RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                            final @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        IPage<GroupVo> page = groupService.page(groupUserVo, current, size);
        return ResponseData.success(page);
    }

    @ApiOperation(value = "群组详情", notes = "群组--获取详情")
    @GetMapping("/{groupId}")
    public ResponseData detail(@PathVariable String groupId) {
        GroupVo groupVo = groupService.detail(groupId);
        return ResponseData.success(groupVo);
    }

    @ApiOperation(value = "拉人入群", notes = "群组--拉人入群")
    @PostMapping("/pull")
    public ResponseData pull(@RequestBody GroupUserJoinVo groupUserJoinVo) {
        groupService.pull(groupUserJoinVo);
        return ResponseData.success();
    }

    @ApiOperation(value = "拉人入群", notes = "群组--拉人入群")
    @PostMapping("/join")
    public ResponseData join(@RequestBody GroupUserJoinVo groupUserJoinVo) {
        groupService.join(groupUserJoinVo);
        return ResponseData.success();
    }

    @ApiOperation(value = "群组消息处理", notes = "好友消息--处理")
    @PutMapping("/msgHandle")
    public ResponseData msgHandle(@RequestBody MsgHandleVo msgHandleVo) {
        groupService.msgHandle(msgHandleVo);
        return ResponseData.success();
    }
}
