package me.foat.articles.aspects;

import me.foat.articles.aspects.annotations.AroundMethod;
import me.foat.articles.aspects.annotations.ChangeParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Foat Akhmadeev
 *         02/06/15
 */
@RestController
public class AspectController {
    private static final Logger log = LoggerFactory.getLogger(ExampleAspect.class);

    @RequestMapping(value = "/multiply", method = RequestMethod.GET)
    @AroundMethod
    public String multiply(HttpServletRequest request, @RequestParam @ChangeParam String number) {
        log.info("Processing multiply method with a number parameter = {}", number);

        String result = internalMethodAdd(number);

        log.info("Internal method returned a result after aspect processing = {}", result);

        return result + " * ";
    }

    @AroundMethod(value = 200)
    private String internalMethodAdd(@ChangeParam String number) {
        log.info("Processing internalMethodAdd method with a number parameter = {}", number);
        return number + " + ";
    }
}
