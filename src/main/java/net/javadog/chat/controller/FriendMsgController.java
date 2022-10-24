package net.javadog.chat.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.javadog.chat.common.base.response.ResponseData;
import net.javadog.chat.model.vo.FriendMsgVo;
import net.javadog.chat.model.vo.MsgHandleVo;
import net.javadog.chat.service.FriendMsgService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description: 好友消息控制器
 * @Author: hdx
 * @Date: 2022/2/16 17:37
 * @Version: 1.0
 */
@RestController
@Slf4j
@Api(tags = "好友消息控制器")
@RequestMapping("friendMsg")
public class FriendMsgController {

    @Resource
    private FriendMsgService friendMsgService;

    @PostMapping
    public ResponseData send(@RequestBody FriendMsgVo friendMsgVo) {
        log.info("消息数据,friendMsgVo = {}", JSONUtil.toJsonStr(friendMsgVo));
        FriendMsgVo resFriendMsgVo = friendMsgService.add(friendMsgVo);
        return ResponseData.success(resFriendMsgVo);
    }

    @ApiOperation(value = "好友消息列表数据", notes = "好友消息--获取列表数据")
    @GetMapping
    public ResponseData get(final FriendMsgVo friendMsgVo,
                            final @RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                            final @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        IPage<FriendMsgVo> page = friendMsgService.page(friendMsgVo, current, size);
        return ResponseData.success(page);
    }

    @ApiOperation(value = "好友消息撤回", notes = "好友消息--撤回")
    @PutMapping("/msgHandle")
    public ResponseData revoke(@RequestBody MsgHandleVo msgHandleVo) {
        friendMsgService.msgHandle(msgHandleVo);
        return ResponseData.success();
    }
}
