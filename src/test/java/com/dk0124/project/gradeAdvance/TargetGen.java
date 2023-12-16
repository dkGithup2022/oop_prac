package com.dk0124.project.gradeAdvance;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class TargetGen {

    private final UserRepository userRepository;

    public Targets generate() {
        return new Targets(userRepository.findAll());
    }


}
