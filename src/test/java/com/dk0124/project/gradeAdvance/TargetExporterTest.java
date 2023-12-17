package com.dk0124.project.gradeAdvance;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class TargetExporterTest {
    @Test
    void 성공_로컬_기록 () throws IOException {
        TargetExporter exporter = new TargetExporter();
        exporter.export(Path.of("build/stulist"), new Targets(Arrays.asList(
                new User(1L), new User(2L)
        )));

        List<String> lines = Files.readAllLines(Path.of("build/stulist"));
        log.info("lines : {}", lines);
    }
}
