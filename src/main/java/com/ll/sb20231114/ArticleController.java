package com.ll.sb20231114;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class ArticleController {
    // 장기 기억하기 위해서 밖으로 뺐다.
    private List<Article> articles = new ArrayList<>(); // 여러개 저장하기 위해서

    // 액션 메서드 만들기
    // 게시글 작성
    @GetMapping("/article/write")
    String showWrite() {
        return "article/write";
    }

    // 글쓰기 버튼 누른 후에 저장
    @GetMapping("/article/doWrite")
    @ResponseBody
    Map<String, Object> doWrite(
            String title,
            String body
    ) {
        // 객체 만들기
        Article article = new Article(articles.size() + 1, title, body);

        // 결과
        // LinkedHashMap : 데이터 순서 기억, HashMap : 데이터 순서 기억 안함
        Map<String, Object> rs = new LinkedHashMap<>();
        rs.put("resultCode", "S-1"); // S : Success 성공
        rs.put("msg", "%d번 게시물이 작성되었습니다.".formatted(article.getId()));
        rs.put("data", article);

        articles.add(article);

        return rs;
    }

    // 가장 마지막에 올라온 게시글 알려준다.
    @GetMapping("/article/getLastArticle")
    @ResponseBody
    Article getLastArticle() {
        return articles.getLast();
    }

    // 모든 게시글 불러온다.
    @GetMapping("/article/getArticles")
    @ResponseBody
    List<Article> getArticles() {
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
