package io.github.allaudin.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configuration annotation for oxygeroid configuration
 *
 * @author M.Allaudin
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface OxyConfig {

    /**
     * Resource package for referering <b>R</b> file
     *
     * @return package name for <em>R</em> file
     */
    String resourcePackage() default "";

    /**
     * Module name for picking layout files
     *
     * @return modulue name for this app
     */
    String module() default "app";


} // OxyConfig
