package org.saas.order.config;


import org.saas.order.interceptor.OrderInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OrderWebConfig implements WebMvcConfigurer {

    private final OrderInterceptor orderInterceptor;

    public OrderWebConfig(OrderInterceptor orderInterceptor) {
        this.orderInterceptor = orderInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(orderInterceptor);
    }
}
