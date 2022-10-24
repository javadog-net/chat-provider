package net.javadog.chat.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.javadog.chat.common.base.response.ResponseData;
import net.javadog.chat.model.vo.InvitationVo;
import net.javadog.chat.service.InvitationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 好友邀请控制器
 * @Author: hdx
 * @Date: 2022/1/13 16:39
 * @Version: 1.0
 */

@RestController
@Api(tags = "好友邀请控制器")
@RequestMapping("/invitation")
public class InvitationController {

    @Resource
    private InvitationService friendInvitationService;

    @ApiOperation(value = "好友邀请", notes = "好友--邀请")
    @PostMapping
    public ResponseData add(final @RequestBody InvitationVo friendInvitationVo) {
        InvitationVo resFriendInvitationVo = friendInvitationService.add(friendInvitationVo);
        return ResponseData.success(resFriendInvitationVo);
    }

    @ApiOperation(value = "好友重新邀请", notes = "好友--重新邀请")
    @PutMapping
    public ResponseData update(final @RequestBody InvitationVo friendInvitationVo) {
        friendInvitationService.update(friendInvitationVo);
        return ResponseData.success();
    }

    @ApiOperation(value = "好友邀请-同意/拒绝", notes = "好友邀请--同意/拒绝")
    @PutMapping("/handle")
    public ResponseData handle(final @RequestBody InvitationVo friendInvitationVo) {
        friendInvitationService.handle(friendInvitationVo);
        return ResponseData.success();
    }

    @ApiOperation(value = "好友邀请列表数据", notes = "好友邀请--获取列表数据")
    @GetMapping
    public ResponseData get(final InvitationVo friendInvitationVo) {
        List<InvitationVo> friendInvitations = friendInvitationService.list(friendInvitationVo);
        return ResponseData.success(friendInvitations);
    }

}
