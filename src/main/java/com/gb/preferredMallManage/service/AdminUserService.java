package com.gb.preferredMallManage.service;

import com.gb.preferredMallManage.entity.AdminUser;

/**
 * 用户业务
 */
public interface AdminUserService {

    /**
     * 登录
     * @param userName
     * @param Password
     * @return
     */
    String login(String userName,String Password);

    /**
     * 根据用户id获取用户信息
     * @param adminUserId
     * @return
     */
    AdminUser getUserDataById(Long adminUserId);

    boolean updatePassword(Long adminUserId, String originalPassword, String newPassword);

    boolean updateAdminUserName(Long adminUserId, String loginUserName, String nickName);

    boolean logout(Long adminUserId);
}
