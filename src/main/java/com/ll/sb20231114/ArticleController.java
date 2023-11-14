package com.ll.sb20231114;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ArticleController {
    // 장기 기억하기 위해서 밖으로 뺐다.
    private Article[] articles = new Article[3]; // 여러개 저장하기 위해서
    private int articlesLastIndex = -1;

    // 액션 메서드 만들기
    // 게시글 작성
    @GetMapping("/article/write")
    String showWrite() {
        return "article/write";
    }

    @GetMapping("/article/doWrite")
    @ResponseBody
    Map<String, Object> doWrite(
            String title,
            String body
    ) {
        // 객체 만들기
        Article article = new Article(articlesLastIndex + 2, title, body);

        // 결과
        Map<String, Object> rs = new HashMap<>();
        rs.put("msg", "%d번 게시물이 작성되었습니다.".formatted(article.getId()));
        rs.put("data", article);

        articles[++articlesLastIndex] = article;

        return rs;
    }

    // 가장 마지막에 올라온 게시글 알려준다.
    @GetMapping("/article/getLastArticle")
    @ResponseBody
    Article getLastArticle() {
        return articles[articlesLastIndex];
    }

    @GetMapping("/article/getArticles")
    @ResponseBody
    Article[] getArticles() {
        return articles;
    }

    @AllArgsConstructor
    @Getter
    class Article {
        private long id;
        private String title;
        private String body;
    }
}
