package com.ll.sb20231114.domain.article.article.service;

import com.ll.sb20231114.domain.article.article.entity.Article;
import com.ll.sb20231114.domain.article.article.repository.ArticleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component// 저는 단 한번만 생성되고, 그 이후에는 재사용되는 객체입니다.
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
