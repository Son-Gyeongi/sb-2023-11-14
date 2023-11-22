package com.ll.sb20231114.domain.home.home.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Controller // 개발자가 스프링부트에게 HomeController 클래스가 컨트롤러라는 걸 알려준다.
public class HomeController {

    @GetMapping("/")
    public String goToArticleList() {
        return "redirect:/article/list";
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
}
