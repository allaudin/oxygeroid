package io.github.allaudin.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Put on any class to generate view factory with that class name.</p>
 * <p>Layout parameter will be representing xml layout file, which will be parsed for
 * views to be added in the view factory.</p>
 * <p>
 * <p>You can <em>optionally</em> specify the class name for generated view factory and
 * the type of object <b>init</b> function will accept in generated view factory.</p>
 *
 * @author M.Allaudin
 * @version 1.0
 *          Created on 2016-12-24 13:57.
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface OxyViews {

    /**
     * Xml file name to be parsed for views.
     * It should not have <b>.xml</b> extention or any other id prefix.
     *
     * @return layout xml name
     */
    String value();

    /**
     * Class name of generated view factory
     *
     * @return classname of generated views class
     */
    String className() default "";

    /**
     * Type of parameter, generated view class will accept in its factory function
     *
     * @return type of views to be passed to generated class
     * @see FactoryType
     */
    FactoryType type() default FactoryType.ACTIVITY;
} // OxyViews
