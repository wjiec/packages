package org.laboys.better.spring.core.web;

import org.laboys.better.spring.core.annotation.web.UndecoratedApi;
import org.laboys.better.spring.core.web.decoration.DecorationResult;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
public class RestResultAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 主程序所在的命名空间
     */
    private final List<String> packages;

    public RestResultAdvice(ApplicationContext context) {
        packages = AutoConfigurationPackages.get(context.getAutowireCapableBeanFactory());
    }

    /**
     * 对于注解了{@link UndecoratedApi}的接口不进行返回值包装
     */
    @Override
    public boolean supports(MethodParameter parameter, Class<? extends HttpMessageConverter<?>> converter) {
        Class<?> controller = parameter.getDeclaringClass();
        if (controller.getAnnotation(UndecoratedApi.class) == null) {
            Method handler = parameter.getMethod();
            if (handler != null && handler.getAnnotation(UndecoratedApi.class) == null) {
                return packages.stream().anyMatch(s -> controller.getPackageName().startsWith(s));
            }
        }

        return false;
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter parameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> converter,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        Object rawBody = body;
        if (body instanceof ResponseEntity) {
            ResponseEntity<?> entity = (ResponseEntity<?>) body;

            rawBody = entity.getBody();
            response.setStatusCode(entity.getStatusCode());
            response.getHeaders().addAll(entity.getHeaders());
        }

        if (!(rawBody instanceof DecorationResult)) {
            return DecorationResult.success(rawBody, new HashMap<>());
        }

        ((DecorationResult<?>) rawBody).mergeExtra(new HashMap<>());
        return rawBody;
    }

}
