package com.ll.sb20231114.domain.article.article.controller;

import com.ll.sb20231114.domain.article.article.entity.Article;
import com.ll.sb20231114.domain.article.article.service.ArticleService;
import com.ll.sb20231114.domain.member.member.entity.Member;
import com.ll.sb20231114.domain.member.member.service.MemberService;
import com.ll.sb20231114.global.rq.Rq;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor // 생성자 주입, final 붙은 필드만 생성
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;
    private final Rq rq;

    // 게시글 리스트
    @GetMapping("/article/list")
    String showList(Model model, HttpServletRequest req) {
        // 쿠키이름이 loginedMemberId 이것인 것의 값을 가져와서 long 타입으로 변환,
        // 만약에 그런게 없다면, 0을 반환
        long loginedMemberId =
                Optional.ofNullable(req.getCookies())
                        .stream()
                        .flatMap(Arrays::stream)
                        .filter(cookie -> cookie.getName().equals("loginedMemberId"))
                        .map(Cookie::getValue)
                        .mapToLong(Long::parseLong)
                        .findFirst()
                        .orElse(0);

        if (loginedMemberId > 0) {
            // 리스트에 로그인된 사용자도 있으면 정보를 보내자
            Member loginedMember = memberService.findById(loginedMemberId).get();
            model.addAttribute("loginedMember", loginedMember);
        }

        long fromSessionLoginedMemberId = 0;

        // 세션이 비어져있어도 세션에 뭔가를 뒤져보는 것만으로도 세션을 만든다.
        // session에 값이 있다면 long으로 형변환
        if (req.getSession().getAttribute("loginedMemberId") != null)
            fromSessionLoginedMemberId = (long) req.getSession().getAttribute("loginedMemberId");

        // 값이 있다면 model에 담아서 list.html로 넘긴다.
        if (fromSessionLoginedMemberId > 0) {
            Member loginedMember = memberService.findById(fromSessionLoginedMemberId).get();
            model.addAttribute("fromSessionLoginedMember", loginedMember);
        }

        List<Article> articles = articleService.findAll();

        // model에 담아서 list.html에 보내기
        model.addAttribute("articles", articles);

        return "article/article/list";
    }

    // 상세페이지
    @GetMapping("/article/detail/{id}")
    String showDetail(Model model, @PathVariable long id) {
        Article article = articleService.findById(id).get(); // Optional이면 0~1개 값이 온다. null이면 프로그램 뻗는다.

        model.addAttribute("article", article); // model에 값을 담아서 detail.html로 준다.

        return "article/article/detail";
    }

    // 게시글 작성
    @GetMapping("/article/write")
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
    @PostMapping("/article/write")
    String write(@Valid WriteForm writeForm) {
        Article article = articleService.write(writeForm.title, writeForm.body);

        return rq.redirect("/article/list", "%d번 게시물 생성되었습니다.".formatted(article.getId()));
    }

    // 게시글 수정 이동
    @GetMapping("/article/modify/{id}")
    String showModify(Model model, @PathVariable long id) {
        Article article = articleService.findById(id).get();

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
    @PostMapping("/article/modify/{id}")
    String modify(@PathVariable long id, @Valid ModifyForm modifyForm) {
        articleService.modify(id, modifyForm.title, modifyForm.body);

        return rq.redirect("/article/list", "%d번 게시물 수정되었습니다.".formatted(id));
    }

    // 게시글 삭제
    @GetMapping("/article/delete/{id}")
    String delete(@PathVariable long id) {
        articleService.delete(id);

        return rq.redirect("/article/list", "%d번 게시물 삭제되었습니다.".formatted(id));
    }
}

