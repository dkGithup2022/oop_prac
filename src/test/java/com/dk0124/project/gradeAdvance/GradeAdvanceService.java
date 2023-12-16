package com.dk0124.project.gradeAdvance;

import java.nio.file.Path;

class GradeAdvanceService {

    private static String STATE_FILE_PATH = "build/state";
    private static String TARGET_FILE_PATH = "build/target";

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
