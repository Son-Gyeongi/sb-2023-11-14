package com.ll.sb20231114;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * ArticleController 부분 다 지우고 다시 만들어보세요. 35분까지
 * 요청
 * GET /article/write
 * GET /article/doWrite?title=제목&body=내용
 * GET /article/getLastArticle
 * GET /article/getArticles
 *
 * 미션 수행하는데 조금 힘들었다 ㅠ 복습하자
 */
@Controller
public class ArticleController {

    List<Article> articles = new ArrayList<>();

    // 글 작성 버튼
    @GetMapping("/article/write")
    String articleWrite() {
        return "article/write";
    }

    // 글 작성 버튼 누른 후 저장
    @GetMapping("/article/doWrite")
    @ResponseBody
    RsData<Article> articleDoWrite(
            String title,
            String body
    ) {
        // 객체 생성
        Article article = new Article(articles.size() + 1, title, body);
        // 리스트에 저장
        articles.add(article);

        // 결과
        RsData<Article> rs = new RsData<>(
                "S-1",
                "%d번 게시글이 저장되었습니다.".formatted(article.getId()),
                article
        );

        return rs;
    }

    // 마지막 글 가져오기
    @GetMapping("/article/getLastArticle")
    Article getLastArticle() {
        return articles.getLast();
    }

    // 모든 글 가져오기
    @GetMapping("/article/getArticles")
    List<Article> getArticles() {
        return articles;
    }
}

@AllArgsConstructor
@Getter
class RsData<T> {
    private String resultCode;
    private String msg;
    private T data;
}

@AllArgsConstructor
@Getter
class Article {
    private long id;
    private String title;
    private String body;
}