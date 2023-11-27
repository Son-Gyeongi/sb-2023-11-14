package com.ll.sb20231114.domain.home.home.controller;

import com.ll.sb20231114.global.rq.Rq;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Controller // 개발자가 스프링부트에게 HomeController 클래스가 컨트롤러라는 걸 알려준다.
@RequiredArgsConstructor
public class HomeController {
    private final Rq rq;

    @GetMapping("/")
    public String goToArticleList(String msg) {
        return rq.redirect("/article/list", msg);
    }

    // 스프링 시큐리티의 세션 구조 확인
    @GetMapping("/home/session")
    @ResponseBody
    public Map<String, Object> showSession(HttpSession session) {
        return Collections.list(session.getAttributeNames()).stream()
                .collect(
                        Collectors.toMap(
                                key -> key,
                                key -> session.getAttribute(key)
                        )
                );
    }

    // 쓰레드 상황
    @GetMapping("/home/test1")
    @ResponseBody
    public String showTest1() {
        return Thread.currentThread().getName(); // http-nio-8020-exec-2, tomcat-handler-21(옵션 추가 후)
    }

    @GetMapping("/home/test2") // /home/test2?name=하하
    @ResponseBody
    public String showTest2(String name) {
        return name + "님 안녕하세요하하!!!";
    }
}
