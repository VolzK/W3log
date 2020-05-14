package com.wanzhk.api.controller;

import com.wanzhk.api.core.AjaxResult;
import com.wanzhk.api.utils.JwtUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 *
 * @author wangzhiheng
 * <p>
 * 2020-05-14
 */
@RestController
public class LoginController extends BaseController {

    /**
     * 登陆
     *
     * @param map 登录对象
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public AjaxResult login(@RequestBody Map<String, Object> map) {
        Map<String, Object> map1 = new HashMap<>();
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        Boolean rememberMe = (Boolean) map.get("rememberMe");

        // 从SecurityUtils里边创建一个 subject
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, DigestUtils.md5Hex(password.getBytes()));
        try {
            // 记住登录
            token.setRememberMe(rememberMe == null ? false : rememberMe);
            getSubject().login(token);


            map1.put("token", JwtUtils.createToken(getLoginUserInfo()));

            return AjaxResult.success("ok", map1);
        } catch (AuthenticationException e) {
            return AjaxResult.fail(e.getMessage());
        }
    }

    /**
     * 注销
     */
    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public AjaxResult logout() {
        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        return AjaxResult.success("成功注销！");
    }

    /**
     * 未登录
     */
    @RequestMapping(value = "/notLogin", method = RequestMethod.GET)
    public AjaxResult notLogin() {
        return AjaxResult.success("您尚未登陆！");
    }

    /**
     * 无权限
     */
    @RequestMapping(value = "/notauth")
    public AjaxResult notRole() {
        return AjaxResult.error("您没有权限！");
    }

}
