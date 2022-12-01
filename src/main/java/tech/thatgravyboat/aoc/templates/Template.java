package tech.thatgravyboat.aoc.templates;

import java.util.List;

public interface Template {

    int day();

    String partOne(List<String> input);

    String partTwo(List<String> input);

    default void run() {
        TemplateLoader.load(this);
    }
}
