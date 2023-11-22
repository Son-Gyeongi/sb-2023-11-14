package com.ll.sb20231114.global.interceptor;

import com.ll.sb20231114.global.rq.Rq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NeedToLoginInterceptor implements HandlerInterceptor {
    private final Rq rq; // 현재 접속한 회원 의미

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("NeedToLoginInterceptor.preHandle 실행됨");

        List<String> authorities = rq.getSessionAttr("authorities");

        if (!rq.isLogined()) {
            throw new RuntimeException("로그인 후 이용해주세요.");
        }

        return true;
    }
}
