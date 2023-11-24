package com.ll.sb20231114.domain.member.member.controller;

import com.ll.sb20231114.domain.member.member.entity.Member;
import com.ll.sb20231114.domain.member.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MemberControllerTest {
    // step 31, ChatGPT 에게 ArticleControllerTest 의 작업 스타일대로
    // MemberControllerTest 도 완성해달라고 부탁

    @Autowired
    private MockMvc mvc;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // GET /member/login
    @Test
    @DisplayName("로그인 페이지를 보여준다")
    @WithAnonymousUser
    // 로그인 되지 않은 사용자
    void t1() throws Exception {
        // When 과 Then 으로 나눔

        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/member/login"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("member/member/login"))
                .andExpect(handler().methodName("showLogin"))
                .andExpect(content().string(containsString("""
                        <input type="text" name="username"
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <input type="password" name="password"
                        """.stripIndent().trim())));
    }

    // step 33, 테스트, POST /member/login, ChatGPT 에게
    // 로그인 폼 처리에 대한 테스트도 만들어 달라고 요청 후 작업

    // POST /member/login
    @Test
    @DisplayName("잘못된 로그인 정보")
    void t2() throws Exception {
        mvc.perform(
                        formLogin("/member/login")
                                .user("username", "user1")
                                .password("password", "12345")
                )
                .andDo(print())
                .andExpect(unauthenticated());
    }

    // POST /member/login
    @Test
    @DisplayName("로그인 처리")
    void t3() throws Exception {
        mvc.perform(
                        formLogin("/member/login")
                                .user("username", "user1")
                                .password("password", "1234")
                )
                .andDo(print())
                .andExpect(authenticated());
    }

    // GET /member/join
    @Test
    @DisplayName("회원가입 폼")
    void t4() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/member/join"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("member/member/join"))
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("showJoin"))
                .andExpect(content().string(containsString("""
                        회원가입
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <input type="text" name="username"
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <input type="password" name="password"
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <input type="password" name="passwordConfirm"
                        """.stripIndent().trim())));
    }

    // POST /member/join
    @Test
    @DisplayName("회원가입 폼 처리")
    void t5() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(
                        post("/member/join")
                                .with(csrf())
                                .param("username", "usernew")
                                .param("password", "1234")
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("join"))
                .andExpect(redirectedUrlPattern("/member/login?msg=**"));

        Member member = memberService.findLatest().get();

        assertThat(member.getUsername()).isEqualTo("usernew");
        assertThat(passwordEncoder.matches("1234", member.getPassword())).isTrue();
    }

    // POST /article/write
    @Test
    @DisplayName("중복된 username 사용은 가입실패")
    void t6() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(
                        post("/member/join")
                                .with(csrf())
                                .param("username", "admin")
                                .param("password", "1234")
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("join"));
    }
}