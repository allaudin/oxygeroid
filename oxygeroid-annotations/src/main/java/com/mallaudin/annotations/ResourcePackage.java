package com.mallaudin.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Identify the resource file package that will be used in generated view factories.</p>
 * <pre>e.g. in; R.id.username, this package will be used for refereing <strong>R</strong>.  </pre>
 *
 * @author M.Allaudin
 * @version 1.0
 *
 * Created on 2016-12-24 13:43.
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface ResourcePackage {
    String value();
    String module() default "app";
} // ResourcePackage
