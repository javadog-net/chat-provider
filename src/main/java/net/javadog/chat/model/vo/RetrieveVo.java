package net.javadog.chat.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author: hdx
 * @Date: 2022-09-06 15:14
 * @version: 1.0
 **/
@Data
@ApiModel("密码找回Vo")
public class RetrieveVo implements Serializable {

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "请输入手机号")
    @Pattern(regexp = "^1[345678]\\d{9}$", message = "手机号格式错误！")
    private String username;

    @ApiModelProperty(value = "邮箱", required = true)
    @NotBlank(message = "请输入邮箱")
    @Pattern(regexp = "^[A-Za-z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "邮箱格式不正确")
    private String email;
}
