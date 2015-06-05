package me.foat.articles.aspects.annotations;

import java.lang.annotation.*;

/**
 * @author Foat Akhmadeev
 *         03/06/15
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AroundMethod {
    int value() default 100;
}
