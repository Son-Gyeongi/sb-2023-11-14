package com.ll.sb20231114.domain.article.article.repository;

import com.ll.sb20231114.domain.article.article.entity.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository // 빈에 등록
public class ArticleRepository {

    // 장기 기억하기 위해서 밖으로 뺐다.
    private List<Article> articles = new ArrayList<>(); // 여러개 저장하기 위해서


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
