package com.mydemo.common.web.context;

import com.mydemo.common.model.constant.CommonConstants;
import com.mydemo.common.model.dto.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * 用户上下文拦截器
 * <p>从网关传递的 Header 中解析用户信息，设入 UserContext</p>
 */
public class UserContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userId = request.getHeader(CommonConstants.HEADER_USER_ID);
        if (StringUtils.hasText(userId)) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            userInfo.setUsername(request.getHeader(CommonConstants.HEADER_USERNAME));
            String nickname = request.getHeader(CommonConstants.HEADER_NICKNAME);
            if (StringUtils.hasText(nickname)) {
                userInfo.setNickname(URLDecoder.decode(nickname, StandardCharsets.UTF_8));
            }
            UserContext.setCurrentUser(userInfo);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        UserContext.clear();
    }
}
