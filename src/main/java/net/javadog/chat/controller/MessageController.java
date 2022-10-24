package net.javadog.chat.controller;

import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.javadog.chat.common.base.response.ResponseData;
import net.javadog.chat.model.vo.MessageVo;
import net.javadog.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 好友消息/群组消息控制器
 * @Author: hdx
 * @Date: 2022/2/16 17:37
 * @Version: 1.0
 */
@RestController
@Slf4j
@Api(tags = "好友消息/群组消息控制器")
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseData list(MessageVo messageVo) {
        log.info("参数数据,messageVo = {}", JSONUtil.toJsonStr(messageVo));
        List<MessageVo> listMessageVo = messageService.list(messageVo);
        return ResponseData.success(listMessageVo);
    }

}
