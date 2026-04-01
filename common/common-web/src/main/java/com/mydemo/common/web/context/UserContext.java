package com.mydemo.common.web.context;

import com.mydemo.common.model.dto.UserInfo;

/**
 * 用户上下文（ThreadLocal）
 */
public final class UserContext {

    private static final ThreadLocal<UserInfo> USER_HOLDER = new ThreadLocal<>();

    private UserContext() {
    }

    public static void setCurrentUser(UserInfo user) {
        USER_HOLDER.set(user);
    }

    public static UserInfo getCurrentUser() {
        return USER_HOLDER.get();
    }

    public static String getCurrentUserId() {
        UserInfo user = USER_HOLDER.get();
        return user != null ? user.getUserId() : null;
    }

    public static void clear() {
        USER_HOLDER.remove();
    }
}
