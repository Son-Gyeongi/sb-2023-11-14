package com.ll.sb20231114.domain.article.article.controller;

import com.ll.sb20231114.domain.article.article.entity.Article;
import com.ll.sb20231114.domain.article.article.service.ArticleService;
import com.ll.sb20231114.global.rsData.RsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ArticleController {
    @Autowired // 필드 주입, final은 뺸다. ArticleService에 @Component로 빈에 등록되어야지 쓸 수 있다.
    private ArticleService articleService;

    // 액션 메서드 만들기
    // 게시글 작성
    @GetMapping("/article/write")
    String showWrite() {
        return "article/write";
    }

    // 글쓰기 버튼 누른 후에 저장
    @PostMapping("/article/write")
    @ResponseBody
    RsData write(
            String title,
            String body
    ) {
        Article article = articleService.write(title, body);

        // 결과
        // Article 버전의 RsData 객체
        RsData<Article> rs = new RsData<>(
                "S-1",
                "%d번 게시물이 작성되었습니다.".formatted(article.getId()),
                article
        );

        return rs;
    }

    // 가장 마지막에 올라온 게시글 알려준다.
    @GetMapping("/article/getLastArticle")
    @ResponseBody
    Article getLastArticle() {
        return articleService.findLastArticle();
    }

    // 모든 게시글 불러온다.
    @GetMapping("/article/getArticles")
    @ResponseBody
    List<Article> getArticles() {
        return articleService.findAll();
    }
}

