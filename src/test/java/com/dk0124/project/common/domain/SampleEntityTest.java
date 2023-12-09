package com.dk0124.project.common.domain;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
class SampleEntityTest {

    @Autowired
    SampleEntityRepository sampleEntityRepository;

    @Test
    public void jpa_의존성_확인() {
        assertNotNull(sampleEntityRepository);
    }

    @Test
    public void h2_연동_확인(){
        //G
        var sample =  new SampleEntity();

        //W
        var saved = sampleEntityRepository.save(sample);
        var found = sampleEntityRepository.findById(saved.getId());

        //T
        assertThat(found.get()).isNotNull();
        assertThat(found.get().getId()).isNotNull();
        assertThat(found.get().getCreatedAt()).isNotNull();
        assertThat(found.get().getUpdatedAt()).isNotNull();
    }
}