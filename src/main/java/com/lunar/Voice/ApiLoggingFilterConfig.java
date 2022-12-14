package com.lunar.Voice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

//@Component
//@ConditionalOnExpression("${app.api.logging.enable:true}")
public class ApiLoggingFilterConfig {

    @Value("${app.api.logging.url-patterns:*}")
    private String[] urlPatterns;

    @Value("${app.api.logging.requestIdParamName:requestId}")
    private String requestIdParamName;

    @Bean
    public FilterRegistrationBean<ApiLoggingFilter> loggingFilter() {
        FilterRegistrationBean<ApiLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiLoggingFilter(requestIdParamName));
        registrationBean.addUrlPatterns(urlPatterns);
        return registrationBean;
    }
}