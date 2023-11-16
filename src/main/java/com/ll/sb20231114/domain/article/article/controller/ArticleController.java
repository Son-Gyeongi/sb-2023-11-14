package com.ll.sb20231114.domain.article.article.controller;

import com.ll.sb20231114.domain.article.article.entity.Article;
import com.ll.sb20231114.domain.article.article.service.ArticleService;
import com.ll.sb20231114.global.rq.Rq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor // 생성자 주입, final 붙은 필드만 생성
public class ArticleController {

    private final ArticleService articleService;
    private final Rq rq;

    @GetMapping("/article/delete/{id}")
    String delete(@PathVariable long id) {
        articleService.delete(id);

        // 결과
        String msg = "id %d, article deleted".formatted(id);

        return "redirect:/article/list?msg=" + msg;
    }

    // 게시글 작성
    @GetMapping("/article/write")
    String showWrite() {
        return "article/write";
    }

    // 상세페이지
    @GetMapping("/article/detail/{id}")
    String showDetail(Model model, @PathVariable long id) {
        Article article = articleService.findById(id).get(); // Optional이면 0~1개 값이 온다. null이면 프로그램 뻗는다.

        model.addAttribute("article", article); // model에 값을 담아서 detail.html로 준다.

        return "article/detail";
    }

    @Data // @Getter, @Setter 쓸 수 있다.
    public static class WriteForm {
        @NotBlank
        private String title;

        @NotBlank
        private String body;

    }

    // 글쓰기 버튼 누른 후에 저장
    @PostMapping("/article/write")
    String write(@Valid WriteForm writeForm) {
        Article article = articleService.write(writeForm.title, writeForm.body);

        // 결과
        String msg = "id %d, article created".formatted(article.getId());

        return "redirect:/article/list?msg=" + msg;
    }

    // 게시글 리스트
    @GetMapping("/article/list")
    String showList(Model model) {
        List<Article> articles = articleService.findAll();

        // model에 담아서 list.html에 보내기
        model.addAttribute("articles", articles);

        return "article/list";
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

