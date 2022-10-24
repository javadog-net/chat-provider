package net.javadog.chat.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Description: 用户密码Vo
 * @Author: hdx
 * @Date: 2022/1/29 15:38
 * @Version: 1.0
 */
@Data
@ApiModel("用户密码Vo")
public class UserPwdVo implements Serializable {

    @ApiModelProperty(value = "老密码", required = true)
    @NotBlank(message = "请输入老密码")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{6,16}$", message = "请录入6-16位老密码！")
    private String oldPwd;

    @ApiModelProperty(value = "新密码", required = true)
    @NotBlank(message = "请输入新密码")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{6,16}$", message = "请录入6-16位新密码！")
    private String newPwd;

    @ApiModelProperty(value = "确认密码", required = true)
    @NotBlank(message = "请输入确认密码")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{6,16}$", message = "请录入6-16位确认密码！")
    private String comfirmPwd;

}
