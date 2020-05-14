// package com.wanzhk.api.configurer;
//
// import com.alibaba.fastjson.JSON;
// import com.wanzhk.api.core.AjaxResult;
// import com.wanzhk.api.utils.IpUtils;
// import com.wanzhk.api.utils.JwtUtils;
// import org.apache.commons.lang3.StringUtils;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Component;
// import org.springframework.web.method.HandlerMethod;
// import org.springframework.web.servlet.HandlerInterceptor;
// import org.springframework.web.servlet.ModelAndView;
//
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
//
// /**
//  * 自定义拦截器，验证Token
//  *
//  * @author wangzhiheng
//  * <p>
//  * 2020-05-14
//  */
// @Component
// public class TokenInterceptor implements HandlerInterceptor {
//
//     private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
//
//     // 实现对数据的预处理
//     @Override
//     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//         System.out.println(1);
//         String token = request.getHeader("token");      // 从 http 请求头中取出 token
//         if (!(handler instanceof HandlerMethod)) {          // 如果不是映射到方法直接通过
//             return true;
//         }
//         if (StringUtils.isNotEmpty(token)) {
//
//
//         }
//
//
//         boolean pass = JwtUtils.verifyToken(token);  //验证签名
//         if (pass) {
//             return true;
//         }
//         logger.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}",
//                 request.getRequestURI(), IpUtils.getIpAddress(request), JSON.toJSONString(request.getParameterMap()));
//         responseResult(response, AjaxResult.error(401, "签名认证失败"));
//         return false;
//     }
//
//
//     // Response 配置
//     private void responseResult(HttpServletResponse response, AjaxResult result) {
//         response.setCharacterEncoding("UTF-8");
//         response.setHeader("Content-type", "application/json;charset=UTF-8");
//         response.setStatus(200);
//         try {
//             response.getWriter().write(JSON.toJSONString(result));
//         } catch (IOException ex) {
//             logger.error(ex.getMessage());
//         }
//     }
//
//     @Override
//     public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//     }
//
//     @Override
//     public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//     }
// }
