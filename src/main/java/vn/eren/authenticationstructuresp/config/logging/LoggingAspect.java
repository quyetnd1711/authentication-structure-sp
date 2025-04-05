package vn.eren.authenticationstructuresp.config.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {
    }

    @Around("controllerMethods()")
    public Object logAroundControllers(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        String path = determineApiPath(method);

        String request = getRequestParams(joinPoint);
        logger.info("REQUEST - API {} : {}", path, request);

        Object result = joinPoint.proceed();

        String response;
        try {
            ObjectMapper mapper = new ObjectMapper();
            response = mapper.writeValueAsString(result);
        } catch (Exception e) {
            response = result != null ? result.toString() : "null";
        }
        logger.info("RESPONSE - API {} : {}", path, response);

        return result;
    }

    private String determineApiPath(Method method) {
        RequestMapping classRequestMapping = method.getDeclaringClass().getAnnotation(RequestMapping.class);
        String baseUrl = "";
        if (classRequestMapping != null && classRequestMapping.value().length > 0) {
            baseUrl = classRequestMapping.value()[0];
        }

        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof GetMapping) {
                String[] values = ((GetMapping) annotation).value();
                return baseUrl + (values.length > 0 ? values[0] : "");
            } else if (annotation instanceof PostMapping) {
                String[] values = ((PostMapping) annotation).value();
                return baseUrl + (values.length > 0 ? values[0] : "");
            } else if (annotation instanceof PutMapping) {
                String[] values = ((PutMapping) annotation).value();
                return baseUrl + (values.length > 0 ? values[0] : "");
            } else if (annotation instanceof DeleteMapping) {
                String[] values = ((DeleteMapping) annotation).value();
                return baseUrl + (values.length > 0 ? values[0] : "");
            } else if (annotation instanceof RequestMapping) {
                String[] values = ((RequestMapping) annotation).value();
                return baseUrl + (values.length > 0 ? values[0] : "");
            }
        }

        return "Unknown Path";
    }

    private String getRequestParams(ProceedingJoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        if (args.length == 0) {
            return "No params";
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Class<?>[] parameterTypes = signature.getParameterTypes();

        StringBuilder paramsInfo = new StringBuilder();

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            String paramName = parameterNames[i];
            Class<?> paramType = parameterTypes[i];

            if (arg != null) {
                if (isBodyParameter(paramType, joinPoint, i)) {
                    paramsInfo.append("Body[").append(paramName).append("]: ");
                    // Sử dụng ObjectMapper để serialize đối tượng sang JSON
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        paramsInfo.append(mapper.writeValueAsString(arg));
                    } catch (Exception e) {
                        paramsInfo.append(arg.toString());
                    }
                } else if (arg instanceof MultipartFile) {
                    MultipartFile file = (MultipartFile) arg;
                    paramsInfo.append("File[").append(paramName).append("]: ")
                            .append(file.getOriginalFilename())
                            .append(" (").append(file.getSize()).append(" bytes)");
                } else {
                    paramsInfo.append(paramName).append("=").append(arg);
                }

                paramsInfo.append(", ");
            }
        }

        if (paramsInfo.length() > 2) {
            paramsInfo.delete(paramsInfo.length() - 2, paramsInfo.length());
        }

        return paramsInfo.toString();
    }

    private boolean isBodyParameter(Class<?> paramType, ProceedingJoinPoint joinPoint, int paramIndex) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Annotation[][] paramAnnotations = method.getParameterAnnotations();
        if (paramIndex < paramAnnotations.length) {
            for (Annotation annotation : paramAnnotations[paramIndex]) {
                if (annotation instanceof RequestBody) {
                    return true;
                }
            }
        }

        return !paramType.isPrimitive() &&
                !paramType.equals(String.class) &&
                !Number.class.isAssignableFrom(paramType) &&
                !paramType.equals(Boolean.class) &&
                !paramType.isArray() &&
                !Collection.class.isAssignableFrom(paramType) &&
                !Map.class.isAssignableFrom(paramType) &&
                !paramType.equals(MultipartFile.class) &&
                !paramType.getName().startsWith("org.springframework");
    }
}
