package com.ll.sb20231114.domain.member.member.controller;

import com.ll.sb20231114.domain.member.member.entity.Member;
import com.ll.sb20231114.domain.member.member.service.MemberService;
import com.ll.sb20231114.global.rq.Rq;
import com.ll.sb20231114.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;

    // 멤버 로그인 GET
    @PreAuthorize("isAnonymous()") // 로그인 되어있지 않은 사용자 접근 가능
    @GetMapping("/member/login")
    String showLogin() {
        return "member/member/login"; // member/member/login.html
    }

    // 멤버 가입 GET
    @PreAuthorize("isAnonymous()")
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
    @PreAuthorize("isAnonymous()")
    @PostMapping("/member/join")
    String join(@Valid JoinForm joinForm) {
        // 컨트롤러에서 예외를 처리하지 않음, 결국 요청처리흐름이 중단됨
        RsData<Member> joinRs = memberService.join(joinForm.username, joinForm.password);

        if (joinRs.isFail()) {
            return rq.historyBack(joinRs.getMsg());
        }

        // 멤버 가입 GET 으로 이동
        return rq.redirect("/member/login", joinRs.getMsg());
    }
}
