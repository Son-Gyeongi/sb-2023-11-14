package com.ll.sb20231114.domain.home.home.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AdmHomeControllerTest {
    @Autowired
    private MockMvc mvc;

    // step 36, 테스트, GET /adm with user1, 403 발생
    @Test
    @DisplayName("관리자가 아니라면 관리자 페이지에 접속할 수 없다.")
    @WithUserDetails("user1")
    void t1() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/adm"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is4xxClientError());
    }

    // step 37, 테스트, GET /adm with admin, 200 발생
    @Test
    @DisplayName("관리자라면 접속할 수 있다.")
    @WithUserDetails("admin")
    void t2() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/adm"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(AdmHomeController.class))
                .andExpect(handler().methodName("showMain"))
                .andExpect(view().name("home/home/adm/main"));
    }

    // step 38 테스트, GET /adm/home/about, 200 발생
    @Test
    @DisplayName("/adm/home/about")
    @WithUserDetails("admin")
    void t3() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/adm/home/about"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(AdmHomeController.class))
                .andExpect(handler().methodName("showAbout"))
                .andExpect(view().name("home/home/adm/about"));
    }
}