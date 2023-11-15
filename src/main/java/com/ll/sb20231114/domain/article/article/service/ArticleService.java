package com.ll.sb20231114.domain.article.article.service;

import com.ll.sb20231114.domain.article.article.entity.Article;
import com.ll.sb20231114.domain.article.article.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.List;

public class ArticleService {

    private final ArticleRepository articleRepository = new ArticleRepository();

    // 게시글 작성
    public Article write(String title, String body) {
        // 객체 만들기
        Article article = new Article(title, body);

        articleRepository.save(article);

        return article;
    }

    // 마지막 게시글 조회
    public Article findLastArticle() {
        return articleRepository.findLastArticle();
    }

    // 모든 게시글 조회
    public List<Article> findAll() {
        return articleRepository.findAll();
    }
}
