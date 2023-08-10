package com.gb.preferredMallManage.dao;

import com.gb.preferredMallManage.entity.AdminUserToken;

public interface preferredAdminUserTokenMapper {

    AdminUserToken selectByPrimaryKey(Long adminUserId);

    int insertSelective(AdminUserToken adminUserToken);

    int updateByPrimaryKeySelective(AdminUserToken adminUserToken);

    int insert(AdminUserToken adminUserToken);

    AdminUserToken selectByToken(String token);

    int deleteByPrimaryKey(Long adminUserId);
}
