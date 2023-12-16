package com.dk0124.project.gradeAdvance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class GradeAdvanceServiceTest {

    private static String STATE_FILE_PATH = "build/state";
    private static String TARGET_FILE_PATH = "build/target";


    TargetGen mockGen = mock(TargetGen.class);

    States states = new States(Paths.get(STATE_FILE_PATH));
    TargetExporter targetExporter = mock(TargetExporter.class);
    TargetApplier targetApplier = mock(TargetApplier.class);
    TargetImporter targetImporter = mock(TargetImporter.class);
    GradeAdvanceService service = new GradeAdvanceService(states, mockGen, targetExporter, targetApplier, targetImporter);


    @BeforeEach
    void 파일_지우기() throws IOException {
        Files.deleteIfExists(Path.of(STATE_FILE_PATH));
    }


    @Test
    void 상태_승급_완료시_진행_안함() {
        states.set(AdvanceState.COMPLETE);
        var alreadyCompleted = service.advance();
        assertThat(alreadyCompleted).isEqualTo(AdvanceResult.ALREADY_COMPLETED);
    }

    @Test
    void 타갯_생성_실패() {
        BDDMockito.given(mockGen.generate()).willThrow(new RuntimeException("!"));
        var advacneResult = service.advance();
        assertThat(advacneResult).isEqualTo(AdvanceResult.TARGET_GEN_FAIL);
    }

    @Test
    void 타겟_익스포트_실패() {
        BDDMockito.given(mockGen.generate()).willReturn(mock(Targets.class));
        BDDMockito.willThrow(new RuntimeException("!!!")).given(targetExporter).export(Mockito.any(), Mockito.any());

        AdvanceResult result = service.advance();

        assertThat(result).isEqualTo(AdvanceResult.TARGET_EXPORT_FAIL);
    }

    @Test
    void 타겟_승급_실패() {
        BDDMockito.given(mockGen.generate()).willReturn(mock(Targets.class));
        BDDMockito.doNothing().when(targetExporter).export(Mockito.any(), Mockito.any());
        BDDMockito.given(targetApplier.apply(Mockito.any())).willThrow(new RuntimeException("!!"));

        AdvanceResult result = service.advance();

        assertThat(result).isEqualTo(AdvanceResult.TARGET_APPLY_FAIL);
    }

    @Test
    void 타겟_승급_성공() {
        BDDMockito.given(mockGen.generate()).willReturn(mock(Targets.class));
        BDDMockito.doNothing().when(targetExporter).export(Mockito.any(), Mockito.any());
        BDDMockito.given(targetApplier.apply(Mockito.any(Targets.class))).willReturn(null);

        AdvanceResult result = service.advance();

        assertThat(result).isEqualTo(AdvanceResult.SUCCESS);
    }

    @Test
    void APPLY_FAIL_상태에서_실행() {
        states.set(AdvanceState.APPLY_FAILED);
        Targets targets = new Targets();
        BDDMockito.given(targetImporter.importTargets(Mockito.any(Path.class))).willReturn(targets);

        service.advance();

        BDDMockito.then(mockGen).shouldHaveNoInteractions();
        BDDMockito.then(targetExporter).shouldHaveNoInteractions();
        BDDMockito.then(targetApplier).should().apply(Mockito.eq(targets));

    }

    private class GradeAdvanceService {
        States states;
        TargetGen targetGen;
        TargetExporter targetExporter;
        TargetApplier targetApplier;
        TargetImporter targetImporter;

        public GradeAdvanceService(States states, TargetGen targetGen, TargetExporter targetExporter, TargetApplier targetApplier, TargetImporter targetImporter) {
            this.states = states;
            this.targetGen = targetGen;
            this.targetExporter = targetExporter;
            this.targetApplier = targetApplier;
            this.targetImporter = targetImporter;
        }

        public AdvanceResult advance() {

            var state = states.get();
            if (state == AdvanceState.COMPLETE)
                return AdvanceResult.ALREADY_COMPLETED;

            Targets targets = null;
            // 뭐든 에러가 나면 ..!

            if (state == AdvanceState.APPLY_FAILED) {
                targets = targetImporter.importTargets(Path.of(TARGET_FILE_PATH));
            } else {
                try {
                    targetGen.generate();
                } catch (Exception e) {
                    return AdvanceResult.TARGET_GEN_FAIL;
                }

                try {
                    targetExporter.export(Path.of(TARGET_FILE_PATH), targets);
                } catch (Exception e) {
                    return AdvanceResult.TARGET_EXPORT_FAIL;
                }
            }

            try {
                targetApplier.apply(targets);
            } catch (Exception e) {
                return AdvanceResult.TARGET_APPLY_FAIL;
            }

            return AdvanceResult.SUCCESS;
        }
    }
}
