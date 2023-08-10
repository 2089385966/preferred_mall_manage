package com.gb.preferredMallManage.api.admin.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class updatePasswordParam {

    @NotEmpty(message = "originalPassword不能为空")
    private String originalPassword;

    @NotEmpty(message = "newPassword不能为空")
    private String newPassword;
}
