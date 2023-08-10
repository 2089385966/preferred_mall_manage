package com.gb.preferredMallManage.config.handler;

import com.gb.preferredMallManage.common.Constants;
import com.gb.preferredMallManage.common.ServiceResultEnum;
import com.gb.preferredMallManage.common.preferredMallException;
import com.gb.preferredMallManage.config.annotation.ToKenToAdminUser;
import com.gb.preferredMallManage.entity.AdminUserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.gb.preferredMallManage.dao.preferredAdminUserTokenMapper;

import javax.annotation.Resource;

@Component
public class TokenToAdminUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Resource
    private preferredAdminUserTokenMapper preferredAdminUserTokenMapper;

    public TokenToAdminUserMethodArgumentResolver() {
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasMethodAnnotation(ToKenToAdminUser.class)){
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
       if (parameter.getParameterAnnotation(ToKenToAdminUser.class) instanceof ToKenToAdminUser){
            String token = webRequest.getHeader("token");
            if (token != null && !"".equals(token) && token.length() == Constants.TOKEN_LENGTH){
                AdminUserToken adminUserToken = preferredAdminUserTokenMapper.selectByToken(token);
                if (adminUserToken == null) {
                    preferredMallException.fail(ServiceResultEnum.ADMIN_NOT_LOGIN_ERROR.getResult());
                } else if (adminUserToken.getExpireTime().getTime() <= System.currentTimeMillis()) {
                    preferredMallException.fail(ServiceResultEnum.ADMIN_TOKEN_EXPIRE_ERROR.getResult());
                }
                return adminUserToken;
            }else {
                preferredMallException.fail(ServiceResultEnum.ADMIN_NOT_LOGIN_ERROR.getResult());
            }
       }
       return null;
    }
}
