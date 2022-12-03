package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;

import java.util.*;

public class Three extends Template {

    private static final String INDEXES = "\0abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {
        new Three().load(3);
    }

    @Override
    public String partOne() {
        int priority = 0;

        for (String s : getInput()) {
            int half = s.length() / 2;
            Set<Character> intersection = intersection(stringToSet(s.substring(0, half)), stringToSet(s.substring(half)));
            for (Character c : intersection) {
                priority += INDEXES.indexOf(c);
            }
        }
        return String.valueOf(priority);
    }

    @Override
    public String partTwo() {
        int priority = 0;
        for (List<String> s : getEveryX(getInput(), 3)) {
            Set<Character> intersection = intersection(stringToSet(s.get(0)), stringToSet(s.get(1)), stringToSet(s.get(2)));
            for (Character c : intersection) {
                priority += INDEXES.indexOf(c);
                break;
            }
        }
        return String.valueOf(priority);
    }

    private static <T> List<List<T>> getEveryX(List<T> list, int x) {
        List<List<T>> lists = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i % x == 0) {
                lists.add(new ArrayList<>(x));
            }
            lists.get(i / x).add(list.get(i));
        }
        return lists;
    }

    private static Set<Character> stringToSet(String s) {
        return new HashSet<>(s.chars().mapToObj(c -> (char) c).toList());
    }

    @SafeVarargs
    private static <T> Set<T> intersection(Set<T> set, Set<T>... sets) {
        Set<T> intersection = new HashSet<>(set);
        for (Set<T> s : sets) {
            intersection.retainAll(s);
        }
        return intersection;
    }
}
