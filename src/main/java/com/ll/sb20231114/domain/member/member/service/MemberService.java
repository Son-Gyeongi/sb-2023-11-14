package com.ll.sb20231114.domain.member.member.service;

import com.ll.sb20231114.domain.member.member.entity.Member;
import com.ll.sb20231114.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 멤버 가입
    public Member join(String username, String password) {
        // 객체 생성
        Member member = new Member(username, password);

        // 멤버 등록
        memberRepository.save(member);

        return member;
    }

    // 모든 멤버 찾기
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    // 멤버 id로 찾기 (1명)
    public Optional<Member> findById(long id) {
        return memberRepository.findById(id);
    }

    // 멤버 삭제
    public void delete(long id) {
        memberRepository.delete(id);
    }

    // 멤버 수정
    public void modify(long id, String username, String password) {
        // 수정할 멤버 찾기
        Member member = findById(id).get();
        member.setUsername(username);
        member.setPassword(password);

        // 지금은 메모리에 저장해서 리포지터리로 일을 맡길게 없다.
    }
}
