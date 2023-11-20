package com.ll.sb20231114.global.rq;

import com.ll.sb20231114.domain.member.member.entity.Member;
import com.ll.sb20231114.domain.member.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RequestScope
@Component
@Getter
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final MemberService memberService;

    public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
        this.req = req;
        this.resp = resp;
        this.memberService = memberService;
    }

    public String redirect(String path, String msg) {
        msg = URLEncoder.encode(msg, StandardCharsets.UTF_8);

        return "redirect:" + path + "?msg=" + msg;
    }

    // 로그인 된 사용자 id 가져오기, 없으면 0 반환
    private long getMemberId() {
        return Optional
                .ofNullable(req.getSession().getAttribute("loginedMemberId"))
                .map(id -> (long) id)
                .orElse(0L);
    }

    public boolean isLogined() {
        return getMemberId() > 0;
    }

    // 로그인 된 멤버 찾기
    public Member getMember() {
        // 로그인된 사용자가 없다면
        if (!isLogined()) {
            return null;
        }

        return memberService.findById(getMemberId()).get();
    }
}
