package com.exam.examservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author LILEI
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptor = registry.addInterceptor(new LoginHandlerInterceptor());
        String[] excludes=new String[]{"/login.html","/login/userLogin","/layui-v2.4.5/**","/userLogin/**","/login/gainValidCode"};
        interceptor.addPathPatterns("/**").excludePathPatterns(excludes);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/login.html");
        WebMvcConfigurer.super.addViewControllers(registry);
    }
}
