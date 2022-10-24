package net.javadog.chat.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.javadog.chat.common.base.response.ResponseData;
import net.javadog.chat.model.vo.GroupMsgVo;
import net.javadog.chat.service.GroupMsgService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description: 群组消息控制器
 * @Author: hdx
 * @Date: 2022/2/16 17:37
 * @Version: 1.0
 */
@RestController
@Slf4j
@Api(tags = "群组消息控制器")
@RequestMapping("groupMsg")
public class GroupMsgController {

    @Resource
    private GroupMsgService groupMsgService;

    @PostMapping
    public ResponseData send(@RequestBody GroupMsgVo groupMsgVo) {
        log.info("消息数据,groupMsgVo = {}", JSONUtil.toJsonStr(groupMsgVo));
        GroupMsgVo resGroupMsgVo = groupMsgService.add(groupMsgVo);
        return ResponseData.success(resGroupMsgVo);
    }

    @ApiOperation(value = "群组消息列表数据", notes = "群组消息--获取列表数据")
    @GetMapping
    public ResponseData get(final GroupMsgVo groupMsgVo,
                            final @RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                            final @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        IPage<GroupMsgVo> page = groupMsgService.page(groupMsgVo, current, size);
        return ResponseData.success(page);
    }

}
