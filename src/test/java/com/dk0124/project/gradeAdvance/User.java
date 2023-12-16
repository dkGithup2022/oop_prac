package com.dk0124.project.gradeAdvance;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "\"User\"")
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;


    private Long grade;

    public User(Long grade) {
        this.grade = grade;
    }
}
