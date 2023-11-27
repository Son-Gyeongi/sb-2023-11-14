package com.ll.sb20231114.domain.base.attr.repository;

import com.ll.sb20231114.domain.base.attr.entity.Attr;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // application-test.yml 가 추가적으로 실행된다.
public class AttrRepositoryTest {

    @Autowired
    private AttrRepository attrRepository;

    /*
    테스트케이스는 실행 후에도 흔적을 남기면 안된다. 그 룰에 따르기 위해서
    @Transactional 과 @Rollback 추가하여 자동으로 테스트에서 수행했던 DB결과가 사라지도록 함
     */
    @DisplayName("attr 저장")
    @Test
    @Transactional // springframework에서 제공하는
    @Rollback
    void t1() {
        Attr attr = Attr.builder()
                .createDate(LocalDateTime.now())
                .name("age")
                .build();
        attrRepository.save(attr);
        assertThat(attr.getId()).isGreaterThan(0L);
    }

    @DisplayName("attr 저장, 한번 더")
    @Test
    @Transactional // springframework에서 제공하는
    @Rollback
    void t2() {
        Attr attr = Attr.builder()
                .createDate(LocalDateTime.now())
                .name("age")
                .build();
        attrRepository.save(attr);
        assertThat(attr.getId()).isGreaterThan(0L);
    }

    @DisplayName("select count(*) from attr")
    @Test
    void t3() {
        assertThat(attrRepository.count()).isEqualTo(0);
    }
}
