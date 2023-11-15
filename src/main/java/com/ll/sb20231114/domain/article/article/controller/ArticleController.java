package com.ll.sb20231114.domain.article.article.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.sb20231114.domain.article.article.entity.Article;
import com.ll.sb20231114.domain.article.article.service.ArticleService;
import com.ll.sb20231114.global.rsData.RsData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor // 생성자 주입, final 붙은 필드만 생성
public class ArticleController {

    private final ArticleService articleService;

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

    // 스프링부트 없던 시절 HttpServletRequest로 요청 받음, HttpServletResponse 응답 전달
    @PostMapping("/article/write2")
    @SneakyThrows // try-catch 자동 생성
    void write2(
            HttpServletRequest req,
            HttpServletResponse resp
    ) {
        String title = req.getParameter("title");
        String body = req.getParameter("body");

        Article article = articleService.write(title, body);

        RsData<Article> rs = new RsData<>(
                "S-1",
                "%d번 게시물이 작성되었습니다.".formatted(article.getId()),
                article
        );

        // 결과
        ObjectMapper objectMapper = new ObjectMapper();

        resp.getWriter().println(objectMapper.writeValueAsString(rs));
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

