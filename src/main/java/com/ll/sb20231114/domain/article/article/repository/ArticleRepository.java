package com.ll.sb20231114.domain.article.article.repository;

import com.ll.sb20231114.domain.article.article.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository // 빈에 등록
@RequiredArgsConstructor
public class ArticleRepository {
    private final List<Article> articles = new ArrayList<>();

    public Article save(Article article) {
        // id값 추가하기
        if (article.getId() == null) { // null을 활용하기 위해서 id필드 Long타입으로 변경
            article.setId(articles.size() + 1L);
        }

        articles.add(article);

        return article;
    }

    public List<Article> findAll() {
        return articles;
    }

    // 상세페이지
    public Optional<Article> findById(long id) {
        return articles.stream()
                .filter(article -> article.getId() == id)
                .findFirst(); // 처음에 일치하는 값을 반환
    }

    public void delete(Article article) {
        articles.remove(article);
    }

    // 마지막 게시글 조회
    public Optional<Article> findLatest() {
        return Optional.ofNullable(
                articles.isEmpty() ? null : articles.getLast()
        );
    }
}
