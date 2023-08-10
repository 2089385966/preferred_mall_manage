package com.gb.preferredMallManage;

import com.gb.preferredMallManage.entity.AdminUser;
import com.gb.preferredMallManage.service.AdminUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class PreferredMallManageApplicationTests {

    @Autowired
    private AdminUserService adminUserService;

    @Test
    void TestLogin() {

        String admin = adminUserService.login("guibin", "123456");
        System.out.println(admin);

    }

    @Test
    void testSystem(){
        long l = System.currentTimeMillis();
        System.out.println(l);
    }

    @Test
    void TestProfile(){
        AdminUser userDataById = adminUserService.getUserDataById(4L);
        System.out.println(userDataById);
    }

}
