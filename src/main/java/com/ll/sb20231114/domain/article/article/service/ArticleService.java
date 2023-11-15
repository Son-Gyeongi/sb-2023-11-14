package com.ll.sb20231114.domain.article.article.service;

import com.ll.sb20231114.domain.article.article.entity.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleService {

    // 장기 기억하기 위해서 밖으로 뺐다.
    private List<Article> articles = new ArrayList<>(); // 여러개 저장하기 위해서

    // 게시글 작성
    public Article write(String title, String body) {
        // 객체 만들기
        Article article = new Article(articles.size() + 1, title, body);
        articles.add(article);

        return article;
    }

    // 마지막 게시글 조회
    public Article findLastArticle() {
        return articles.getLast();
    }

    // 모든 게시글 조회
    public List<Article> findAll() {
        return articles;
    }
}
