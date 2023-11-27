package com.ll.sb20231114.domain.base.attr.repository;

import com.ll.sb20231114.domain.base.attr.entity.Attr;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // application-test.yml 가 추가적으로 실행된다.
public class AttrRepositoryTest {

    @Autowired
    private AttrRepository attrRepository;

    @Test
    @DisplayName("attr 저장")
    void t1() {
        Attr attr = Attr.builder()
                .createDate(LocalDateTime.now())
                .name("age")
                .build();
        attrRepository.save(attr);
        assertThat(attr.getId()).isGreaterThan(0L);
    }
}
