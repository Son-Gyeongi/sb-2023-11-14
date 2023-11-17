package com.ll.sb20231114.domain.member.member.repository;

import com.ll.sb20231114.domain.member.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    // 메모리에 등록
    private final List<Member> members = new ArrayList<>() {{
        // 익명 클래스 생성자
        add(new Member(1L, "user1", "1234"));
        add(new Member(2L, "user2", "1234"));
        add(new Member(3L, "user3", "1234"));
    }};

    // 멤버 등록
    public Member save(Member member) {
        if (member.getId() == null) {
            member.setId(members.size() + 1L);
        }

        members.add(member);

        return member;
    }

    // 멤버 모두 찾기
    public List<Member> findAll() {
        return members;
    }

    // 멤버 id로 찾기
    public Optional<Member> findById(long id) {
        return members.stream() // List를 스트림화 시킨다.
                .filter(member -> member.getId() == id) // id가 같은 걸 찾는다.
                .findFirst(); // 제일 첫번째로 찾는 걸 가져온다.
    }

    // 멤버 삭제
    public void delete(long id) {
        members.removeIf(member -> member.getId() == id);
    }
}
