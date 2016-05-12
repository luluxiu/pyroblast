package com.tigercel;

import com.tigercel.service.SViewHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by freedom on 2016/4/13.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SViewHelper svh;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(viewObjectAddingInterceptor());
        super.addInterceptors(registry);
    }


    @Bean
    public HandlerInterceptor viewObjectAddingInterceptor() {
        return new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                    throws Exception {

                svh.setStartTime(System.currentTimeMillis());
                if(svh.getPath() == null) {
                    svh.setPath(request.getContextPath());
                }
                if(svh.getUsername() == null) {
                    SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                            .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
                    if(securityContextImpl != null && securityContextImpl.getAuthentication() != null &&
                            securityContextImpl.getAuthentication().getName() != null) {

                        svh.setUsername(securityContextImpl.getAuthentication().getName());
                    }
                }

                return true;
            }
        };
    }
}
