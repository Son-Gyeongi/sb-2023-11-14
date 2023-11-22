package com.ll.sb20231114.domain.member.member.entity;

import lombok.*;

import java.util.List;

// Member 엔티티
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Member {
    @EqualsAndHashCode.Include // 값 비교를 id로 한다.
    private Long id;
    private String username;
    private String password;

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean isAdmin() {
        return username.equals("admin");
    }

    public List<String> getAuthorities() {
        if (isAdmin()) {
            return List.of("ROLE_ADMIN");
        }

        return List.of("ROLE_MEMBER");
    }
}
