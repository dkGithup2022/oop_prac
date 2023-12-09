package com.dk0124.project.common.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dk0124.project.common.domain.QSampleEntity.sampleEntity;


@Repository
@RequiredArgsConstructor
public class SampleQueryDslRespository {

    private final JPAQueryFactory queryFactory;

    List<SampleEntity> listSample(Long id) {
       return queryFactory
                .selectFrom(sampleEntity)
                .where(sampleEntity.id.eq(id))
                .fetch();
    }

}
