package com.company.config;

import com.company.filter.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredFilerConfig {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {

        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(jwtTokenFilter);
        bean.addUrlPatterns("/api/v1/bank/*");
        bean.addUrlPatterns("/api/v1/profile/*");
        bean.addUrlPatterns("/api/v1/bank-account/*");
        return bean;
    }

}
