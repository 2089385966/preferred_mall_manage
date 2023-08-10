package com.gb.preferredMallManage.dao;

import com.gb.preferredMallManage.entity.AdminUser;

public interface AdminUserMapper {
    AdminUser login(String userName, String password);

    AdminUser selectByPrimaryKey(Long adminUserId);

    int updateByPrimaryKeySelective(AdminUser adminUser);
}
