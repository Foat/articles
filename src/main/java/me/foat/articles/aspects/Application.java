package me.foat.articles.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;

/**
 * remove comments to enable spring aop
 *
 * @author Foat Akhmadeev
 *         02/06/15
 */
@EnableAutoConfiguration
@EnableAspectJAutoProxy
@ComponentScan(
        value = "me.foat.articles.aspects",
        includeFilters = @ComponentScan.Filter({Controller.class, Aspect.class}),
        useDefaultFilters = false)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}