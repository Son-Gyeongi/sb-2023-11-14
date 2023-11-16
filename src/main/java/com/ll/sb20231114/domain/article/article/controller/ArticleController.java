package com.ll.sb20231114.domain.article.article.controller;

import com.ll.sb20231114.domain.article.article.entity.Article;
import com.ll.sb20231114.domain.article.article.service.ArticleService;
import com.ll.sb20231114.global.rq.Rq;
import com.ll.sb20231114.global.rsData.RsData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor // 생성자 주입, final 붙은 필드만 생성
public class ArticleController {

    private final ArticleService articleService;
    private final Rq rq;

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
        if (title == null || title.trim().length() == 0) {
            throw new IllegalArgumentException("제목을 입력해주세요.");
            // 해당 요청을 처리하는 쓰레드가 정지
        }

        if (body == null || body.trim().length() == 0) {
            throw new IllegalArgumentException("내용을 입력해주세요.");
        }

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

    // == 범위(scope) 알아보기
    // ArticleService 는 싱글톤으로 딱 1번만 객체 생성되고 그 이후에는 무조건 해당 객체가 공유되는 방식이다, 절대 없어지지 않는다.
    @GetMapping("/article/articleServicePointer")
    @ResponseBody
    String articleServicePointer() {
        return articleService.toString();
    }

    // HttpServletRequest, HttpServletResponse 객체는 금방 사라진다.
    @GetMapping("/article/httpServletRequestPointer")
    @ResponseBody
    String httpServletRequestPointer(HttpServletRequest req) {
        return req.toString();
    }

    @GetMapping("/article/httpServletResponsePointer")
    @ResponseBody
    String httpServletResponsePointer(HttpServletResponse resp) {
        return resp.toString();
    }
    // 범위(scope) 알아보기 ==

    @GetMapping("/article/rqPointer")
    @ResponseBody
    String rqPointer() {
        return rq.toString();
    }

    @GetMapping("/article/rqTest")
    String showRqTest(Model model) {
        String rqToString = rq.toString();

        model.addAttribute("rqToString", rqToString);

        return "article/rqTest";
    }
}

