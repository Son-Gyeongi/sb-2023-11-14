package com.ll.sb20231114.domain.article.article.service;

import com.ll.sb20231114.domain.article.article.entity.Article;
import com.ll.sb20231114.domain.article.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 저는 단 한번만 생성되고, 그 이후에는 재사용되는 객체입니다.
@RequiredArgsConstructor // 생성자 주입, final 붙은 필드만 생성
public class ArticleService {

    // null이 들어가지 않게 진짜 객체(new)를 만들어 줘야 한다.
    private final ArticleRepository articleRepository;

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
