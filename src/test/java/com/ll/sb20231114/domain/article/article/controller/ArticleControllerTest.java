package com.ll.sb20231114.domain.article.article.controller;

import com.ll.sb20231114.domain.article.article.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ArticleControllerTest {
/*
컨트롤러 테스트에는 @SpringBootTest, @AutoConfigureMockMvc,
@ActiveProfiles("test") 어노테이션이 필요하고
MockMvc mvc 객체로 실제 HTTP 요청을 테스트할 수 있습니다.
 */

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ArticleService articleService;

    // GET /article/list
    @Test
    @DisplayName("게시물 목록 페이지를 보여준다.")
    void t1() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/article/list"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("showList"))
                .andExpect(content().string(containsString("""
                        게시글 목록
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        3번 : 제목3
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        2번 : 제목2
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        1번 : 제목1
                        """.stripIndent().trim())));
    }

    // GET /article/detail/{id}
    // GET /article/write
    // POST /article/write
    // GET /article/modify/{id}
    // PUT /article/modify/{id}
    // DELETE /article/delete/{id}
}