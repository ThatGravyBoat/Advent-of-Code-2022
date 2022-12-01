package tech.thatgravyboat.aoc.templates;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class TemplateLoader {

    public static void load(int day, Template template) {
        try(BufferedReader reader = Files.newBufferedReader(Path.of("inputs", day + ".txt"))) {
            template.loadData(reader.lines().toList());
            System.out.println("Part 1: " + template.partOne());
            System.out.println("Part 2: " + template.partTwo());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
