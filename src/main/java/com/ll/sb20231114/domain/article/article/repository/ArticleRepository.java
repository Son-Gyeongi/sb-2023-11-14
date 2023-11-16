package com.ll.sb20231114.domain.article.article.repository;

import com.ll.sb20231114.domain.article.article.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository // 빈에 등록
@RequiredArgsConstructor
public class ArticleRepository {

    // 장기 기억하기 위해서 밖으로 뺐다.
//    private List<Article> articles = new ArrayList<>(); // 여러개 저장하기 위해서

    // AppConfig 만들어서 빈 관리하기, AppConfig에 있는 메서드명이랑 같은 것이 매칭된다.
    private final List<Article> articles = new ArrayList<>() {{
        add(new Article(1L, "제목 1", "내용 1"));
        add(new Article(2L, "제목 2", "내용 2"));
        add(new Article(3L, "제목 3", "내용 3"));
    }};

    public Article save(Article article) {
        // id값 추가하기
        if (article.getId() == null) { // null을 활용하기 위해서 id필드 Long타입으로 변경
            article.setId(articles.size() + 1L);
        }

        articles.add(article);

        return article;
    }

    public Article findLastArticle() {
        return articles.getLast();
    }

    public List<Article> findAll() {
        return articles;
    }
}
