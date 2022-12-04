package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;

import java.util.*;

public class Four extends Template {

    public static void main(String[] args) {
        new Four().load(4);
    }

    private final List<Range[]> sections = new ArrayList<>();

    @Override
    public void loadData(List<String> input) {
        for (String s : input) {
            String[] section = s.split(",");
            String[] first = section[0].split("-");
            String[] second = section[1].split("-");
            sections.add(new Range[]{
                new Range(Integer.parseInt(first[0]), Integer.parseInt(first[1])),
                new Range(Integer.parseInt(second[0]), Integer.parseInt(second[1]))
            });
        }
    }

    @Override
    public String partOne() {
        int count = 0;
        for (Range[] section : sections) {
            if (section[0].inRange(section[1]) || section[1].inRange(section[0])) {
                count++;
            }
        }
        return Integer.toString(count);
    }

    @Override
    public String partTwo() {
        int count = 0;
        for (Range[] section : sections) {
            Set<Integer> first = section[0].getAsSet();
            first.retainAll(section[1].getAsSet());
            if (!first.isEmpty()) {
                count++;
            }
        }
        return Integer.toString(count);
    }

    private record Range(int start, int end) {

        private Set<Integer> getAsSet() {
            Set<Integer> set = new HashSet<>();
            for (int i = start; i <= end; i++) {
                set.add(i);
            }
            return set;
        }

        public boolean inRange(Range value) {
            return value.start >= start && value.start <= end && value.end >= start && value.end <= end;
        }

    }
}
