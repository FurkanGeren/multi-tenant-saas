package org.saas.product.config;

import org.saas.product.interceptor.ProductInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ProductWebConfig implements WebMvcConfigurer {

    private final ProductInterceptor productInterceptor;

    public ProductWebConfig(ProductInterceptor productInterceptor) {
        this.productInterceptor = productInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(productInterceptor);
    }
}
