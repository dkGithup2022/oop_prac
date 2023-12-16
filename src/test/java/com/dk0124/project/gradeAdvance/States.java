package com.dk0124.project.gradeAdvance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

class States {
    private static String STATE_FILE_PATH = "build/state";

    public States(Path path) {
    }

    public AdvanceState get() {
        if (!Files.exists(Path.of(STATE_FILE_PATH)))
            return null;

        List<String> lines = null;
        try {
            lines = Files.readAllLines(Path.of(STATE_FILE_PATH));
            return AdvanceState.valueOf(lines.get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(AdvanceState state) {
        try {
            Files.writeString(Path.of(STATE_FILE_PATH), state.name(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
