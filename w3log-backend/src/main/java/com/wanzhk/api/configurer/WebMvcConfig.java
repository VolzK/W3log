package com.wanzhk.api.configurer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc 配置
 *
 * @author Wanzhk
 * <p>
 * 2020-05-08
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    public static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    // @Autowired
    // private TokenInterceptor interceptor;


    /**
     * 解决跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        logger.info("解决CORS可以问题");
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    /**
     * 自定义拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("自定义拦截器");

        // List<String> excludePath = new ArrayList<>();
        // excludePath.add("/user_register"); //注册
        // excludePath.add("/api/user/login"); //登录
        // excludePath.add("/logout"); //登出
        // excludePath.add("/static/**");  //静态资源
        // excludePath.add("/assets/**");  //静态资源

        //注册TestInterceptor拦截器
        // registry.addInterceptor(interceptor)
        //         .addPathPatterns("/api/**")
        //         .excludePathPatterns("/api/user/login");
                // .excludePathPatterns(excludePath);
    }

}
