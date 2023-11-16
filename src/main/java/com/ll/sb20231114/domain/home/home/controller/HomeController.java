package com.ll.sb20231114.domain.home.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 개발자가 스프링부트에게 HomeController 클래스가 컨트롤러라는 걸 알려준다.
public class HomeController {

    @GetMapping("/")
    public String goToArticleList() {
        return "redirect:/article/list";
    }
}
