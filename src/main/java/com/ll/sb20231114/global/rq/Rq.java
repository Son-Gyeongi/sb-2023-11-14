package com.ll.sb20231114.global.rq;

import com.ll.sb20231114.domain.member.member.entity.Member;
import com.ll.sb20231114.domain.member.member.service.MemberService;
import com.ll.sb20231114.global.rsData.RsData;
import com.ll.sb20231114.standard.util.Ut;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RequestScope
@Component
@Getter
@RequiredArgsConstructor
@Slf4j
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final MemberService memberService;
    private User user;
    private Member member;
    // 멤버는 장기기억 했으면 좋겠다. final 안 쓰는 이유 member 생성된 후에 값을 넣어야 하므로

    // 생성자가 실행된 후에 @PostConstructor가 실행됨
    @PostConstruct
    public void init() {
        // 현재 로그인한 회원의 인증정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            this.user = (User) authentication.getPrincipal();
        }
    }

    public String redirect(String path, RsData<?> rs) {
        return redirect(path, rs.getMsg());
    }

    public String redirect(String path, String msg) {
        if (msg == null) return "redirect:" + path;

        boolean containsTtl = msg.contains(";ttl=");

        if (containsTtl) { // ttl이 있다면 빼고 밑에서 인코딩 후 다시 붙인다.
            msg = msg.split(";ttl=", 2)[0];
        }

        msg = URLEncoder.encode(msg, StandardCharsets.UTF_8);

        msg += ";ttl=" + (new Date().getTime() + 1000 * 5); // 5초 경고창 유지

        return "redirect:" + path + "?msg=" + msg;
    }

    // 로그인 된 사용자 username 가져오기, 없으면 0 반환
    private String getMemberUsername() {
        return user.getUsername();
    }

    public boolean isLogined() {
        // 로그인된 사용자가 없다면 null이다.
        return user != null;
    }

    // 로그인 된 멤버 찾기
    public Member getMember() {
        // 로그인된 사용자가 없다면
        if (!isLogined()) {
            return null;
        }

        if (member == null)
            // username으로 Member찾기
            member = memberService.findByUsername(getMemberUsername()).get();

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
        if (!isLogined()) {
            return false;
        }

        return user.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public String historyBack(RsData<?> rs) {
        return historyBack(rs.getMsg());
    }

    // 회원가입 중복시 js.html을 갔다가 뒤로가기로 다시 회원가입 폼을 보여준다.
    public String historyBack(String msg) {
        resp.setStatus(400);
        req.setAttribute("msg", msg); // model.addAttribute("msg", msg)하는 거랑 같다.

        return "global/js";
    }

    public String historyBack(Exception ex) {
        String exStr = Ut.exception.toString(ex);
        req.setAttribute("exStr", exStr);

        log.info(exStr);

        return historyBack(ex.getMessage());
    }

    public String redirectOrBack(String url, RsData<?> rs) {
        if (rs.isFail()) return historyBack(rs);
        return redirect(url, rs);
    }
}
