package com.gb.preferredMallManage.service.impl;

import com.gb.preferredMallManage.common.ServiceResultEnum;
import com.gb.preferredMallManage.dao.AdminUserMapper;
import com.gb.preferredMallManage.entity.AdminUser;
import com.gb.preferredMallManage.entity.AdminUserToken;
import com.gb.preferredMallManage.service.AdminUserService;
import com.gb.preferredMallManage.util.NumberUtil;
import com.gb.preferredMallManage.util.SystemUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Resource
    private com.gb.preferredMallManage.dao.preferredAdminUserTokenMapper preferredAdminUserTokenMapper;



    @Override
    public String login(String userName, String Password) {
        //数据库查询数据
        AdminUser adminUser = adminUserMapper.login(userName,Password);
        //修改登录token
        if (adminUser != null){
            //生成新token
            String token = getNewToken(System.currentTimeMillis() + "",adminUser.getAdminUserId());
            //根据登录id查看是否存在token
            AdminUserToken  adminUserToken = preferredAdminUserTokenMapper.selectByPrimaryKey(adminUser.getAdminUserId());
            //token生成时间
            Date now = new Date();
            //token过期时间
            Date expireTime = new Date(now.getTime() + 2 * 24 * 3600 * 1000);//过期时间48小时
            //如查询数据为null
            if (adminUserToken == null){
                adminUserToken = new AdminUserToken();
                adminUserToken.setAdminUserId(adminUser.getAdminUserId());
                adminUserToken.setToken(token);
                adminUserToken.setUpdateTime(now);
                adminUserToken.setExpireTime(expireTime);
                //新增token数据
                if (preferredAdminUserTokenMapper.insertSelective(adminUserToken) > 0) {
                    return token;
                }
                //如查询token数据不为null，则修改token与生成时间与过期时间
            }else {
                adminUserToken.setToken(token);
                adminUserToken.setUpdateTime(now);
                adminUserToken.setExpireTime(expireTime);
                if (preferredAdminUserTokenMapper.updateByPrimaryKeySelective(adminUserToken) > 0 ){
                    return token;
                }
            }
        }

        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }

    /**
     * 获取token值
     *
     * @param timeStr
     * @param userId
     * @return
     */
    private String getNewToken(String timeStr, Long userId) {
        String src = timeStr + userId + NumberUtil.genRandomNum(6);
        return SystemUtil.genToken(src);
    }

    @Override
    public AdminUser getUserDataById(Long adminUserId) {
         return adminUserMapper.selectByPrimaryKey(adminUserId);
    }

    @Override
    public boolean updatePassword(Long adminUserId, String originalPassword, String newPassword) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(adminUserId);
        //查询用户是否存在
        if (adminUser != null){
            //比较密码是否相等
            if (originalPassword.equals(adminUser.getLoginPassword())){
                //设置新密码
                adminUser.setLoginPassword(newPassword);
                if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0 &&
                        preferredAdminUserTokenMapper.deleteByPrimaryKey(adminUserId) > 0){
                    //修改密码并删除token
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean updateAdminUserName(Long adminUserId, String loginUserName, String nickName) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(adminUserId);
        if (adminUser != null){
            adminUser.setLoginUserName(loginUserName);
            adminUser.setNickName(nickName);
            if (adminUserMapper.updateByPrimaryKeySelective(adminUser) >0 ){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean logout(Long adminUserId){
        return preferredAdminUserTokenMapper.deleteByPrimaryKey(adminUserId) > 0;
    }


}
