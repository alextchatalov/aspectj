package com.example.aspectj.log.aspect;

import com.example.aspectj.log.annotation.Loggable;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final ObjectMapper objectMapper;

    public LoggingAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Around("@annotation(loggable) || @within(loggable)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {
        // Get method signature
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // Get class and method names
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        // Check if we need to override the annotation from class level
        Loggable methodLoggable = ((MethodSignature) joinPoint.getSignature())
                .getMethod().getAnnotation(Loggable.class);

        // Method-level annotation takes precedence over class-level
        if (methodLoggable != null) {
            loggable = methodLoggable;
        }

        // Create logger for the class being intercepted
        Logger logger = LoggerFactory.getLogger(methodSignature.getDeclaringType());

        // Log parameters if configured to do so
        logParameters(joinPoint, loggable, methodSignature, logger, methodName, className);

        long startTime = System.currentTimeMillis();
        Object resultAsString = "";
        var result = joinPoint.proceed();
        try {
            // Execute the actual method
            resultAsString = objectMapper.writeValueAsString(joinPoint.proceed());
            return result;
        } catch (Throwable e) {
            logger.error("Exception in method [{}] in class [{}]: {}",
                    methodName, className, e.getMessage());
            throw e;
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;

            // Log result if configured to do so
            if (loggable.logResult()) {
                logWithLevel(logger, loggable.level(),
                        "Exiting method [{}] in class [{}] with result: {} (execution time: {} ms)",
                        methodName, className, resultAsString, executionTime);
            } else {
                logWithLevel(logger, loggable.level(),
                        "Exiting method [{}] in class [{}] (execution time: {} ms)",
                        methodName, className, executionTime);
            }
        }
    }

    private void logParameters(ProceedingJoinPoint joinPoint, Loggable loggable, MethodSignature methodSignature, Logger logger, String methodName, String className) {
        if (loggable.logParameters()) {
            Object[] parameterValues = joinPoint.getArgs();

            String argsAsString;
            try {
                argsAsString = objectMapper.writeValueAsString(parameterValues);
            } catch (Exception e) {
                argsAsString = "Could not serialize method parameters";
            }

            logWithLevel(logger, loggable.level(),
                    "Entering method [{}] in class [{}] with parameters: {}",
                    methodName, className, argsAsString);
        } else {
            logWithLevel(logger, loggable.level(),
                    "Entering method [{}] in class [{}]",
                    methodName, className);
        }
    }

    private void logWithLevel(Logger logger, Loggable.LogLevel level, String format, Object... arguments) {
        switch (level) {
            case TRACE:
                logger.trace(format, arguments);
                break;
            case DEBUG:
                logger.debug(format, arguments);
                break;
            case INFO:
                logger.info(format, arguments);
                break;
            case WARN:
                logger.warn(format, arguments);
                break;
            case ERROR:
                logger.error(format, arguments);
                break;
            default:
                logger.info(format, arguments);
        }
    }
}
