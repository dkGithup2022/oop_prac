package com.dk0124.project.gradeAdvance;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.FileCopyUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StatesTest {
    private static String STATE_FILE_PATH = "build/state";

    States states = new States(Paths.get(STATE_FILE_PATH));

    @BeforeEach
    void 파일_지우기() throws IOException {
        Files.deleteIfExists(Path.of(STATE_FILE_PATH));
    }

    @Test
    public void 진행_상태_없음() {
        AdvanceState state = states.get();
        assertThat(state).isNull();
    }


    @Test
    public void 상태_설정() throws IOException {
        states.set(AdvanceState.GENERATING);
        List<String> lines = Files.readAllLines(Path.of(STATE_FILE_PATH));
        assertThat(lines.get(0)).isEqualTo(AdvanceState.GENERATING.name());
    }

    @Test
    public void 상태_조회() throws IOException {
        FileCopyUtils.copy(AdvanceState.GENERATING.name(), new FileWriter(Path.of(STATE_FILE_PATH).toFile()));

        AdvanceState state = states.get();

        assertThat(state).isEqualTo(AdvanceState.GENERATING);
    }


    private class States {
        public States(Path path) { }

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

        public void set(AdvanceState state) throws IOException {
            Files.writeString(Path.of(STATE_FILE_PATH), state.name(), StandardOpenOption.CREATE);
        }
    }


    private enum AdvanceState {
        GENERATING
    }
}
