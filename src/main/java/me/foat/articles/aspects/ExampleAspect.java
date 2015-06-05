package me.foat.articles.aspects;

import me.foat.articles.aspects.annotations.AroundMethod;
import me.foat.articles.aspects.annotations.ChangeParam;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * @author Foat Akhmadeev
 *         03/06/15
 */
@Aspect
public class ExampleAspect {
    private static final Logger log = LoggerFactory.getLogger(ExampleAspect.class);

    @Around("execution(@me.foat.articles.aspects.annotations.AroundMethod * *.*(..)) && @annotation(change)")
    public Object greetings(ProceedingJoinPoint joinPoint, AroundMethod change) throws Throwable {
        log.info("Annotation value = {}", change.value());

        // all method parameters
        final Object[] args = joinPoint.getArgs();
        // method information
        final Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        final Annotation[][] pa =
                method.getParameterAnnotations();

        // get index of a parameter with ChangeParam annotation
        int idx = getParameterIdx(pa, method.getName());
        Object arg = args[idx];

        if (!(arg instanceof String)) {
            throw new IllegalArgumentException(String.format(
                    "Incorrect argument class in method %s, class is %s, required String",
                    method.getName(), arg.getClass()));
        }

        String number = (String) arg;
        log.info("Input number = {}", number);

        // change input parameter
        number = "(" + number;
        args[idx] = number;

        // display result of a method processing
        final Object result = joinPoint.proceed(args);
        log.info("Method {} returned a result = {}", method.getName(), result);

        // override method return value
        return "" + result + change.value() + ")";
    }

    private int getParameterIdx(Annotation[][] pa, String methodName) {
        OptionalInt optIdx = IntStream
                .range(0, pa.length)
                .filter(i ->
                        Arrays
                                .stream(pa[i])
                                .filter(a -> a instanceof ChangeParam)
                                .findAny()
                                .isPresent())
                .findFirst();

        if (!optIdx.isPresent()) {
            throw new NoSuchElementException(String.format(
                    "Parameter with annotation ChangeParam was not found in a method %s", methodName));
        }

        return optIdx.getAsInt();
    }
}
