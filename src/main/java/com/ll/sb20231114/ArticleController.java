package com.ll.sb20231114;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class ArticleController {
    // 장기 기억하기 위해서 밖으로 뺐다.
    private List<Article> articles = new ArrayList<>(); // 여러개 저장하기 위해서

    // 액션 메서드 만들기
    // 게시글 작성
    @GetMapping("/article/write")
    String showWrite() {
        return "article/write";
    }

    // 글쓰기 버튼 누른 후에 저장
    @GetMapping("/article/doWrite")
    @ResponseBody
    RsData doWrite(
            String title,
            String body
    ) {
        // 객체 만들기
        Article article = new Article(articles.size() + 1, title, body);
        articles.add(article);

        // 결과
        RsData rs = new RsData(
                "S-1",
                "%d번 게시물이 작성되었습니다.".formatted(article.getId()),
                article
        );

        // Object의 한계
        // 항상 추상적인 거에서 구체적인 거로 갈때 수동 형변환 해줘야 한다.
        // 넣을 때는 좋은데 뺄 때는 형변환 해야한다.
        String resultCode = rs.getResultCode();
        String msg = rs.getMsg();
        Article _article = rs.getData();

        return rs;
    }

    // 가장 마지막에 올라온 게시글 알려준다.
    @GetMapping("/article/getLastArticle")
    @ResponseBody
    Article getLastArticle() {
        return articles.getLast();
    }

    // 모든 게시글 불러온다.
    @GetMapping("/article/getArticles")
    @ResponseBody
    List<Article> getArticles() {
        return articles;
    }
}

// 보고서 양식 - 다른데서도 쓴다.
@AllArgsConstructor
@Getter
class RsData { // ResultData라는 뜻
    private String resultCode;
    private String msg;
    private Article data;
}

@AllArgsConstructor
@Getter
class Article {
    private long id;
    private String title;
    private String body;
}
