package com.gb.preferredMallManage.api.admin;


import com.gb.preferredMallManage.api.admin.param.AdminLoginParam;
import com.gb.preferredMallManage.api.admin.param.updatePasswordParam;
import com.gb.preferredMallManage.api.admin.param.updateUserNameParam;
import com.gb.preferredMallManage.common.Constants;
import com.gb.preferredMallManage.common.ServiceResultEnum;
import com.gb.preferredMallManage.config.annotation.ToKenToAdminUser;
import com.gb.preferredMallManage.dao.preferredAdminUserTokenMapper;
import com.gb.preferredMallManage.entity.AdminUser;
import com.gb.preferredMallManage.entity.AdminUserToken;
import com.gb.preferredMallManage.service.AdminUserService;
import com.gb.preferredMallManage.util.Result;
import com.gb.preferredMallManage.util.ResultGenerator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@Api(value = "v1",tags = "后台管理系统管理员模块")
@RequestMapping("/manage-api-v1")
public class preferredAdminManageUserController {

    @Resource
    private AdminUserService adminUserService;

    private static final Logger logger = LoggerFactory.getLogger(preferredAdminManageUserController.class);

    /**
     * 管理员用户登录
     * @param adminLoginParam
     * @return
     */
    @RequestMapping(value = "/admin/login",method = RequestMethod.POST)
    public Result<String> login(@RequestBody AdminLoginParam adminLoginParam){

        String loginResult =  adminUserService.login(adminLoginParam.getUserName(),adminLoginParam.getPasswordMd5());
        logger.info("manage login api,adminName={},loginResult={}",adminLoginParam.getUserName(),loginResult);

        //登录成功
        if (StringUtils.hasText(loginResult) && loginResult.length() == Constants.TOKEN_LENGTH){
            Result result = ResultGenerator.genSuccessResult();
            result.setData(loginResult);
            return result;
        }

        //登录失败
        return ResultGenerator.genSuccessResult(loginResult);
    }

    /**
     * 获取管理员信息
     * @param adminUserToken
     * @return
     */
    @RequestMapping(value = "/admin/profile" , method = RequestMethod.GET)
    public Result profile(@ToKenToAdminUser AdminUserToken adminUserToken){
        logger.info("adminUser:{}",adminUserToken.toString());
        AdminUser adminUser =  adminUserService.getUserDataById(adminUserToken.getAdminUserId());
        if (adminUser != null){
            adminUser.setLoginPassword("********");
            Result result = ResultGenerator.genSuccessResult();
            result.setData(adminUser);
            return result;
        }
        return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
    }

    @RequestMapping(value = "/admin/password" , method = RequestMethod.PUT)
    public Result updateAdminUserPassword(@RequestBody @Valid updatePasswordParam updatePasswordParam
                                            ,@ToKenToAdminUser AdminUserToken adminUserToken){
        logger.info("adminUser:{}",adminUserToken.toString());
        if (adminUserService.updatePassword(adminUserToken.getAdminUserId(),updatePasswordParam.getOriginalPassword()
                                            ,updatePasswordParam.getNewPassword())){
            return ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult(ServiceResultEnum.DB_ERROR.getResult());
        }
    }

    @RequestMapping(value = "/admin/userName",method = RequestMethod.PUT)
    public Result updateAdminUserName(@RequestBody @Valid updateUserNameParam updateUserNameParam,
                                      @ToKenToAdminUser AdminUserToken adminUserToken){
        logger.info("adminUser:{}",adminUserToken.toString());
        if (adminUserService.updateAdminUserName(adminUserToken.getAdminUserId(),
                                                updateUserNameParam.getLoginUserName(),
                                                updateUserNameParam.getNickName())){
            return ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult(ServiceResultEnum.DB_ERROR.getResult());
        }
    }

    @RequestMapping(value = "/admin/logout",method = RequestMethod.DELETE)
    public Result logout(@ToKenToAdminUser AdminUserToken adminUserToken){
        logger.info("adminUser:{}",adminUserToken.toString());
        adminUserService.logout(adminUserToken.getAdminUserId());
        return ResultGenerator.genSuccessResult();
    }

}
