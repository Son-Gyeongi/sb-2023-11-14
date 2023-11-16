package com.ll.sb20231114.domain.article.article.service;

import com.ll.sb20231114.domain.article.article.entity.Article;
import com.ll.sb20231114.domain.article.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    // 모든 게시글 조회
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    // 상세페이지
    public Optional<Article> findById(long id) {
        return articleRepository.findById(id);
    }

    public void delete(long id) {
        articleRepository.delete(id);
    }

    public void modify(long id, String title, String body) {
        Article article = findById(id).get();
        article.setTitle(title);
        article.setBody(body);
        // 위에 로직은 서비스에서 할 일 save 같은 부분이 리포지터리에서 할일
        // 지금은 메모리에 담아서 저장해서 리포지터리까지 가지 않는다.
    }
}
