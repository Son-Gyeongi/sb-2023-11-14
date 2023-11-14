package com.ll.sb20231114;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticleController {

    // 액션 메서드 만들기
    @GetMapping("/article/write")
    String showWrite() {
        return "article/write";
    }
}
