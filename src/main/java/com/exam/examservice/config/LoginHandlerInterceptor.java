package com.exam.examservice.config;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LILEI
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        Object user=request.getSession().getAttribute("loginUser");
        System.out.println("***************"+user);
        if (user==null){
            //未登录，返回登录页面
            request.setAttribute("msg","没有权限，请先登录!");
//            request.getRequestDispatcher("/login").forward(request,response);
            response.sendRedirect("login.html");
            response.getWriter().write("500");
            return false;
        }else {
            //已登录放行
            return true;
        }

    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView){

    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex){

    }
}
