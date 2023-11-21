package com.ll.sb20231114.domain.article.article.repository;

import com.ll.sb20231114.domain.article.article.entity.Article;
import com.ll.sb20231114.domain.member.member.entity.Member;
import com.ll.sb20231114.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository // 빈에 등록
@RequiredArgsConstructor
public class ArticleRepository {
    private final MemberRepository memberRepository;

    private final List<Article> articles = new ArrayList<>() {{
        Member member1 = memberRepository.findById(1L).get();
        Member member2 = memberRepository.findById(2L).get();

        articles.add(new Article(member1, "title1", "content1"));
        articles.add(new Article(member2, "title2", "content2"));
        articles.add(new Article(member2, "title3", "content3"));
    }};

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

    public void delete(long id) {
        articles.removeIf(article -> article.getId() == id);
    }
}
