package org.saas.core.annotation;


import org.saas.core.domain.enums.ModuleType;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ModuleAccess {
    ModuleType value();
}
