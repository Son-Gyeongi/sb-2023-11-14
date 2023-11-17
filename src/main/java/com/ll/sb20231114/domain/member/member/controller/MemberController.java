package com.ll.sb20231114.domain.member.member.controller;

import com.ll.sb20231114.domain.member.member.entity.Member;
import com.ll.sb20231114.domain.member.member.service.MemberService;
import com.ll.sb20231114.global.rq.Rq;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;

    // 멤버 로그인 GET
    @GetMapping("/member/login")
    String showLogin() {
        return "member/member/login"; // member/member/login.html
    }

    @Data
    public static class LoginForm {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    // 멤버 로그인 POST
    @PostMapping("/member/login")
    String login(@Valid LoginForm loginForm,
                 HttpServletResponse response) {
        // 멤버 찾기
        Member member = memberService.findByUsername(loginForm.username).get();

        // 유효성 검사 - 비밀번호 일치 여부 확인
        if (!member.getPassword().equals(loginForm.password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        // 로그인 처리

        // 응답에 쿠키 보내기
        Cookie cookie = new Cookie("loginedMemberId", "2");
        cookie.setPath("/");
        response.addCookie(cookie);

        return rq.redirect("/article/list", "로그인이 완료되었습니다.");
    }

    // 멤버 가입 GET
    @GetMapping("/member/join")
    String showJoin() {
        return "member/member/join"; // member/member/join.html
    }

    @Data // @Getter, @Setter 등 다양한 어노테이션들이 모여있다.
    public static class JoinForm {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    // 멤버 가입 POST
    @PostMapping("/member/join")
    String join(@Valid MemberController.JoinForm joinForm) {
        Member member = memberService.join(joinForm.username, joinForm.password);

        // 멤버 가입 GET 으로 이동
        return rq.redirect("/member/login", "회원가입이 완료되었습니다.");
    }
}
