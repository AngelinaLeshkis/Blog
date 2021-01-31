package com.example.blog.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);
    private String email;

    @Pointcut("execution(* com.example.blog.security.JwtUserDetailsService.loadUserByUsername(*))")
    public void checkRole(){}

    @Pointcut("execution(* com.example.blog.security.JwtUserDetailsService.loadUserByUsername(String)) && args(email)")
    public void checkEmailArg(String email){

    }

    @After("checkRole()")
    public void afterLogin(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature());
    }

    @After(value = "checkEmailArg(email)")
    public void beforeLogin(String email) {
        this.email = email;
        logger.info(this.email);
    }

}
