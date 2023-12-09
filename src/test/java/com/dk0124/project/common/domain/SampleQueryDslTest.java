package com.dk0124.project.common.domain;

import com.dk0124.project.config.rdms.QuerydslConfiguration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@SpringBootTest
@Import(QuerydslConfiguration.class)
public class SampleQueryDslTest {

    @Autowired
    SampleEntityRepository repository;

    @Autowired
    SampleQueryDslRespository jpaQueryFactory;

    @Test
    public void 의존성_주입_확인() {
        assertThat(repository).isNotNull();
        assertThat(jpaQueryFactory).isNotNull();
    }

    @Test
    public void h2_연동_확인(){
        //G
        //G
        var sample =  new SampleEntity();

        //W
        var saved = repository.save(sample);
        var found = jpaQueryFactory.listSample(saved.getId());

        //T
        assertThat(found.get(0)).isNotNull();
        assertThat(found.get(0).getId()).isNotNull();
        assertThat(found.get(0).getCreatedAt()).isNotNull();
        assertThat(found.get(0).getUpdatedAt()).isNotNull();

    }
}
