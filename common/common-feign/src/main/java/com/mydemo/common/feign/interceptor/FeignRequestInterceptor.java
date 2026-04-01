package com.mydemo.common.feign.interceptor;

import com.mydemo.common.model.constant.CommonConstants;
import com.mydemo.common.web.context.UserContext;
import com.mydemo.common.model.dto.UserInfo;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * Feign 请求拦截器：传递用户上下文 Header
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        UserInfo user = UserContext.getCurrentUser();
        if (user != null) {
            template.header(CommonConstants.HEADER_USER_ID, user.getUserId());
            if (user.getUsername() != null) {
                template.header(CommonConstants.HEADER_USERNAME, user.getUsername());
            }
            if (user.getNickname() != null) {
                template.header(CommonConstants.HEADER_NICKNAME, user.getNickname());
            }
        }
    }
}
