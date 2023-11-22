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
    private Member member;
    // 멤버는 장기기억 했으면 좋겠다. final 안 쓰는 이유 member 생성된 후에 값을 넣어야 하므로

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

        if (member == null)
            member = memberService.findById(getMemberId()).get();

        return member;
    }

    // 세션 추가 - 로그인
    public void setSessionAttr(String name, Object value) {
        req.getSession().setAttribute(name, value);
    }

    // 세션 가져오기
    public <T> T getSessionAttr(String name) {
        return (T) req.getSession().getAttribute(name);
    }

    // 세션 삭제 - 로그아웃
    public void removeSessionAttr(String name) {
        req.getSession().removeAttribute(name);
    }

    // 회원 권한 확인
    public boolean isAdmin() {
        return getMember().isAdmin();
    }
}
