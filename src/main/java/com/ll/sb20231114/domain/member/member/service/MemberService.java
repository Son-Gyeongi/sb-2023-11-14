package com.ll.sb20231114.domain.member.member.service;

import com.ll.sb20231114.domain.member.member.entity.Member;
import com.ll.sb20231114.domain.member.member.repository.MemberRepository;
import com.ll.sb20231114.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 멤버 가입
    public RsData<Member> join(String username, String password) {
        if (findByUsername(username).isPresent()) {
            return new RsData<>("F-1", "이미 사용중인 아이디입니다.");
        }

        password = passwordEncoder.encode(password);
        // 객체 생성
        Member member = new Member(username, password);

        // 멤버 등록
        memberRepository.save(member);

        return new RsData<>(
                "S-1",
                "%s님 환영합니다".formatted(member.getUsername()),
                member
        );
    }

    // 모든 멤버 찾기
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    // 멤버 id로 찾기 (1명)
    public Optional<Member> findById(long id) {
        return memberRepository.findById(id);
    }

    // 멤버 username으로 찾기 (1명)
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
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

    public Optional<Member> findLatest() {
        return memberRepository.findLatest();
    }
}
