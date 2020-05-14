package com.wanzhk.api.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 基类
 *
 * @author Wanzhk
 * <p>
 * 2020-05-08
 */
public class BaseController {

    /**
     * 获取subject对象
     */
    Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 是否登录
     *
     * @return
     */
    public boolean isLogin() {
        return getSubject().isAuthenticated();
    }


}
