package com.dk0124.project.gradeAdvance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class TargetImporter {
    public Targets importTargets(Path path) {
        if (!Files.exists(path)) {
            throw new NoTargetFileExistException("!");
        }
        try {
            List<String> lines = Files.readAllLines(path);
            List<User> users = lines.stream().map(line -> {
                String[] splitted = line.split("=");
                return new User(splitted[0] == "null" ? null : Long.valueOf(splitted[0]), Long.valueOf(splitted[1]));
            }).collect(Collectors.toList());
            return new Targets(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
