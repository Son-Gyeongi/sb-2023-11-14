package com.ll.sb20231114.domain.member.member.controller;

import com.ll.sb20231114.domain.member.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

    // GET /member/login
    @Test
    @DisplayName("로그인 페이지를 보여준다")
    @WithAnonymousUser // 로그인 되지 않은 사용자
    void showLoginPage() throws Exception {
        mvc.perform(get("/member/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("member/member/login"))
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("showLogin"));
    }

    // GET /member/join
    @Test
    @DisplayName("회원가입 페이지를 보여준다")
    @WithAnonymousUser
    void showJoinPage() throws Exception {
        mvc.perform(get("/member/join"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("member/member/join"))
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("showJoin"));
    }

    // POST /member/join
    @Test
    @DisplayName("회원가입 처리")
    @WithAnonymousUser
    void joinMember() throws Exception {
        mvc.perform(post("/member/join")
                        .with(csrf())
                        .param("username", "newuser")
                        .param("password", "newpassword"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/member/login?msg=**"))
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("join"));
    }
}