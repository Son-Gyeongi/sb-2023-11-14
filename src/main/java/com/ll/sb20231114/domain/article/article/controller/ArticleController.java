package com.ll.sb20231114.domain.article.article.controller;

import com.ll.sb20231114.domain.article.article.entity.Article;
import com.ll.sb20231114.domain.article.article.service.ArticleService;
import com.ll.sb20231114.global.rq.Rq;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor // 생성자 주입, final 붙은 필드만 생성
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final Rq rq;

    // 게시글 리스트
    @GetMapping("/list")
    String showList(Model model) {
        List<Article> articles = articleService.findAll();

        // model에 담아서 list.html에 보내기
        model.addAttribute("articles", articles);

        return "article/article/list";
    }

    // 상세페이지
    @GetMapping("/detail/{id}")
    String showDetail(Model model, @PathVariable long id) {
        Article article = articleService.findById(id).get(); // Optional이면 0~1개 값이 온다. null이면 프로그램 뻗는다.

        model.addAttribute("article", article); // model에 값을 담아서 detail.html로 준다.

        return "article/article/detail";
    }

    // 게시글 작성
    @PreAuthorize("isAuthenticated()") // 로그인한 사용자만 접근 가능
    @GetMapping("/write")
    String showWrite() {
        return "article/article/write";
    }

    @Data // @Getter, @Setter 쓸 수 있다.
    public static class WriteForm {
        @NotBlank
        private String title;

        @NotBlank
        private String body;
    }

    // 글쓰기 버튼 누른 후에 저장
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    String write(@Valid WriteForm writeForm) {
        Article article = articleService.write(rq.getMember(), writeForm.title, writeForm.body);

        return rq.redirect("/article/list", "%d번 게시물 생성되었습니다.".formatted(article.getId()));
    }

    // 게시글 수정 이동
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    String showModify(Model model, @PathVariable long id) {
        Article article = articleService.findById(id).get();
        // 값이 없어서 null이 오면 프로그램 뻗음

        // 권한 여부 체크는 서비스에서 해야함
        if (!articleService.canModify(rq.getMember(), article))
            throw new RuntimeException("수정권한이 없습니다.");

        model.addAttribute("article", article);

        return "article/article/modify";
    }

    // 게시글 수정 데이터
    @Data // @Getter, @Setter 쓸 수 있다.
    public static class ModifyForm {
        @NotBlank
        private String title;

        @NotBlank
        private String body;
    }

    // 게시글 수정
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    String modify(@PathVariable long id, @Valid ModifyForm modifyForm) {
        Article article = articleService.findById(id).get();
        // 값이 없어서 null이 오면 프로그램 뻗음

        // 권한 여부 체크는 서비스에서 해야함
        if (!articleService.canModify(rq.getMember(), article))
            throw new RuntimeException("수정권한이 없습니다.");

        articleService.modify(article, modifyForm.title, modifyForm.body);

        return rq.redirect("/article/list", "%d번 게시물 수정되었습니다.".formatted(id));
    }

    // 게시글 삭제
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    String delete(@PathVariable long id) {
        Article article = articleService.findById(id).get();
        // 값이 없어서 null이 오면 프로그램 뻗음

        // 권한 여부 체크는 서비스에서 해야함
        if (!articleService.canDelete(rq.getMember(), article))
            throw new RuntimeException("삭제권한이 없습니다.");

        articleService.delete(article);

        return rq.redirect("/article/list", "%d번 게시물 삭제되었습니다.".formatted(id));
    }
}

