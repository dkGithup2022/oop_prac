package com.dk0124.project.gradeAdvance;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class TargetExporter {

    private static String TARGET_FILE_PATH = "build/target";

    public void export(Path path, Targets targets) {
        try (BufferedWriter bw  = Files.newBufferedWriter(path)){
            for(User user :targets.getUsers()){
                bw.write(user.getId()+"="+ user.getGrade());
                bw.newLine();
            }
        }catch (Exception e){
            throw  new RuntimeException( e);
        }
    }
}
