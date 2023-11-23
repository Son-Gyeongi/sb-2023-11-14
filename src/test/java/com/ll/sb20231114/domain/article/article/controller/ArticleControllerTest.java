package com.ll.sb20231114.domain.article.article.controller;

import com.ll.sb20231114.domain.article.article.entity.Article;
import com.ll.sb20231114.domain.article.article.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        // 하드코딩 대신에 articleService.findLatest() 사용
        Article article = articleService.findLatest().get();

        // THEN
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("showList"))
                .andExpect(content().string(containsString("""
                        게시글 목록
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        %d번 : %s
                        """.formatted(article.getId(), article.getTitle())
                        .stripIndent().trim())));
    }

    // GET /article/detail/{id}
    // 게시물 내용이 잘 작동된다고 할 수 있는 조건을 기술하시면 됩니다,
    // 실제 1번 게시물 객체를 가져와서 둘을 대조해 보면 좋습니다.
    @Test
    @DisplayName("게시물 내용 페이지를 보여준다.")
    void t2() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/article/detail/1"))
                .andDo(print());

        // THEN
        Article article = articleService.findById(1L).get();

        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("showDetail"))
                .andExpect(content().string(containsString("""
                        게시글 내용
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <div class="badge badge-outline">1</div>
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString(article.getTitle())))
                .andExpect(content().string(containsString(article.getBody())));
    }

    // GET /article/write
    @Test
    @DisplayName("게시물 작성 페이지를 보여준다.")
    @WithUserDetails("user1") // 사용자 user1이라는 가정
    void t3() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/article/write"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("showWrite"))
                .andExpect(content().string(containsString("""
                        게시글 작성
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <input type="text" name="title"
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <textarea name="body"
                        """.stripIndent().trim())));
    }

    // POST /article/write
    // 정말로 작성이 되었는지 확인하기 위해서 articleService.findLatest() 사용
    @Test
    @DisplayName("게시물을 작성한다.")
    @WithUserDetails("user1")
    void t4() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(
                        post("/article/write")
                                .with(csrf())
                                .param("title", "제목 new")
                                .param("body", "내용 new")
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("write"))
                .andExpect(redirectedUrlPattern("/article/list?msg=**"));

        // 마지막 게시물 조회
        Article article = articleService.findLatest().get();

        assertThat(article.getTitle()).isEqualTo("제목 new");
        assertThat(article.getBody()).isEqualTo("내용 new");
    }

    // GET /article/modify/{id}
    // 실패 예시, 실패하면 테스트는 성공
    @Test
    @DisplayName("작성자가 아니라면 수정폼을 볼 수 없다.")
    @WithUserDetails("user1")
    void t5() throws Exception {
        // WHEN
        assertThrows(Exception.class, () -> {
            ResultActions resultActions = mvc
                    .perform(get("/article/modify/1"))
                    .andDo(print());
        });
    }

    // GET /article/modify/{id}
    // 성공 예시
    @Test
    @DisplayName("게시물 수정폼 페이지를 보여준다.")
    @WithUserDetails("admin") // admin은 1번
    void t6() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/article/modify/1"))
                .andDo(print());

        // THEN
        Article article = articleService.findById(1L).get();

        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("showModify"))
                .andExpect(content().string(containsString("""
                        게시글 수정
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <input type="text" name="title" value="%s"
                        """.formatted(article.getTitle()).stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <textarea name="body"
                        """.formatted(article.getBody()).stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        >%s</textarea>
                        """.formatted(article.getBody()).stripIndent().trim())))
        ;
    }

    // PUT /article/modify/{id}
    @Test
    @DisplayName("게시물 수정폼 처리")
    @WithUserDetails("admin")
    void t7() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(
                        put("/article/modify/1")
                                .with(csrf())
                                .param("title", "제목 new")
                                .param("body", "내용 new")
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("modify"))
                .andExpect(redirectedUrlPattern("/article/list?msg=**"));

        Article article = articleService.findById(1L).get();

        assertThat(article.getTitle()).isEqualTo("제목 new");
        assertThat(article.getBody()).isEqualTo("내용 new");
    }

    // DELETE /article/delete/{id}
}