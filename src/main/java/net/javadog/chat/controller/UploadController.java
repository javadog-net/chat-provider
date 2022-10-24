package net.javadog.chat.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.javadog.chat.common.base.response.ResponseData;
import net.javadog.chat.common.exception.ServiceException;
import net.javadog.chat.common.util.FileUtils;
import net.javadog.chat.common.vo.FileVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Description: 文件上传
 * @Author: hdx
 * @Date: 2022/2/9 14:22
 * @Version: 1.0
 */
@RestController
@Api(tags = "上传控制器")
public class UploadController {

    @ApiOperation(value = "文件上传", notes = "文件上传")
    @PostMapping("/upload")
    public ResponseData upload(MultipartFile file) {
        FileVo fileVo;
        try {
            fileVo = FileUtils.upload(file);
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
        return ResponseData.success(fileVo);
    }
}
