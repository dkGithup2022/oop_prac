package com.dk0124.project.gradeAdvance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("local")
@Transactional
public class TargetsGenTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void dependency() {
        TargetGen targetGen = new TargetGen(userRepository);
    }

    @Test
    void 생성_성공() {
        TargetGen targetGen = new TargetGen(userRepository);
        var u1 = givenUser();
        var u2 = givenUser();
        var u3 = givenUser();
        userRepository.saveAll(List.of(u1, u2, u3));

        Targets targets = targetGen.generate();

        assertThat(targets.getUsers().size()).isEqualTo(3);
    }

    User givenUser() {
        return new User(1L);
    }


}



