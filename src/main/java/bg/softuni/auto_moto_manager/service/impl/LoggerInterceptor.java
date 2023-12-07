package bg.softuni.auto_moto_manager.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggerInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws Exception {

        LOGGER.info("Received request: {} {} from {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getRemoteAddr());

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                @NotNull Object handler,
                                Exception ex) {

        LOGGER.info("Sent response: {} {} with status {} and exception {}",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                ex!= null ? ex.getMessage() : "null");
    }
}
