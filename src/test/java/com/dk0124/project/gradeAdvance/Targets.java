package com.dk0124.project.gradeAdvance;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
public class Targets {
    @Getter
    private  List<User> users ;

    public Targets(List<User> users) {
        this.users = users;
    }
}
