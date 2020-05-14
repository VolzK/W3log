package com.wanzhk.api.controller;

import cn.hutool.core.bean.BeanUtil;
import com.wanzhk.api.modules.entity.TbUser;
import com.wanzhk.api.modules.vo.UserVo;
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

    /**
     * 获取登录信息
     *
     * @return
     */
    public UserVo getLoginUserInfo() {
        TbUser tbUser = (TbUser) getSubject().getPrincipal();
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(tbUser, userVo);
        return userVo;
    }
}
