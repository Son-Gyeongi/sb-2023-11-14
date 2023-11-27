package com.ll.sb20231114.domain.base.system.service;

import com.ll.sb20231114.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemService {
    private final MemberRepository memberRepository;

    public boolean isSampleDataCreated() {
        return memberRepository.count() > 0;
    }
}
