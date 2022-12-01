package tech.thatgravyboat.aoc.templates;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class TemplateLoader {

    public static void load(Template template) {
        try(BufferedReader reader = Files.newBufferedReader(Path.of("inputs", template.day() + ".txt"))) {
            List<String> input = reader.lines().toList();
            System.out.println("Part 1: " + template.partOne(new ArrayList<>(input)));
            if(template.partTwo(input) != null) {
                System.out.println("Part 2: " + template.partTwo(new ArrayList<>(input)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
