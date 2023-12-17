package com.dk0124.project.gradeAdvance;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TargetApplier {

    private final UserRepository userRepository;

    public ApplyResult apply(Targets targets) {
        targets.getUsers().stream()
                .forEach(e->{
                    e.setGrade(e.getGrade()+1);
                    userRepository.save(e);
                });
        return null;
    }
}
