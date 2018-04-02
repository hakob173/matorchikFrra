package com.example.dummy.core.network.spec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiSpec {

    String DOES_NOT_MATTER = "DOES_NOT_MATTER";
    String TO_BE_DOCUMENTED = "TO_BE_DOCUMENTED";
    String EMPTY_ENVELOPE = "{\"Result\": \"OK\"}";
    String EMPTY_ENVELOPE_WITH_MESSAGE = "{\"Result\": \"OK\", \"Message\": \"\"}";


    ApiMethod method();

    String comment() default "";

    String url();

    String jsonRequest() default DOES_NOT_MATTER;

    String jsonResponse() default EMPTY_ENVELOPE;
}
