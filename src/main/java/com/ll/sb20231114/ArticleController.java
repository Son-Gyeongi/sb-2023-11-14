package com.ll.sb20231114;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ArticleController {

    // 액션 메서드 만들기
    @GetMapping("/article/write")
    String showWrite() {
        return "article/write";
    }

    @GetMapping("/article/doWrite")
    @ResponseBody
    Article doWrite(
            String title,
            String body
    ) {
        // 객체 만들기
        Article article = new Article(1,title, body);

        return article;
    }

    @AllArgsConstructor
    @Getter
    class Article {
        private long id;
        private String title;
        private String body;
    }
}
