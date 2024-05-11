package com.example.kubernetes.common.component.interceptor;

import com.example.kubernetes.common.component.security.JwtProvider;
import com.example.kubernetes.user.model.User;
import com.example.kubernetes.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtProvider jwtProvider;
    private final UserRepository repository;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
//        String token = jwtProvider.extractTokenFromHeader(request);
//        log.info("1-Interceptor Token 로그 Bearer 포함: {}", token);
//
//        if (token.equals("undefined")) {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
//            return false;
//        }
//
//        Long id = jwtProvider.getPayload(token).get("id", Long.class); //claims 안에 있음
//        log.info("2- Interceptor 사용자 id : {}" + id);
//
//        Optional<User> user = repository.findById(id);
//        log.info("3- Interceptor 사용자 정보 : {}", user);
//
//        if (!user.isPresent()) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//            return false;
//        }
//        log.info("4- Interceptor 최종 여부 : {}", true);

        return Stream.of(request)
                .map(i -> jwtProvider.extractTokenFromHeader(i))
                .filter(i -> !i.equals("undefined"))
                .peek(i -> log.info("1-Interceptor Token 로그 Bearer 포함: {}", i))
                .map(i -> jwtProvider.getPayload(i).get("id", Long.class))
                .map(i -> repository.findById(i))
                .peek(i -> log.info("2- Interceptor 사용자 id : {}" + i))
                .findFirst()
                .isPresent();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // TODO Auto-generated method stub
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
