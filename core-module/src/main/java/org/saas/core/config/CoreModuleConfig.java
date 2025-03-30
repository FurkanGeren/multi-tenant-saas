package org.saas.core.config;

import org.saas.core.aspect.ModuleAccessAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreModuleConfig {

    @Bean
    @ConditionalOnMissingBean
    public ModuleAccessResolver fallbackModuleAccessResolver() {
        return (tenantId, moduleType) -> true; // her şeye izin verir, sadece test içindir
    }

    @Bean
    public ModuleAccessAspect moduleAccessAspect(ModuleAccessResolver moduleAccessResolver) {
        return new ModuleAccessAspect(moduleAccessResolver);
    }

}
