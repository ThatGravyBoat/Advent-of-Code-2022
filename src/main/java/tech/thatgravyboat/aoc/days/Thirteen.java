package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.IntMatcher;
import tech.thatgravyboat.aoc.utils.Pair;
import tech.thatgravyboat.aoc.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Thirteen extends Template {

    public static void main(String[] args) {
        new Thirteen().load(13);
    }

    private final List<Pair<Object, Object>> values = new ArrayList<>();

    @Override
    protected void onInputLoaded() {
        for (List<String> pair : Util.groupLists(getInput(), 3)) {
            values.add(new Pair<>(
                parse(pair.get(0), 0, '\0').left(),
                parse(pair.get(1), 0, '\0').left()
            ));
        }
    }

    @Override
    public String partOne() {
        int sum = 0;
        for (int i = 0; i < values.size(); i++) {
            if (check(values.get(i).left(), values.get(i).right()) == 1) {
                sum+=i + 1;
            }
        }
        return Integer.toString(sum);
    }

    @Override
    public String partTwo() {
        Object divider1 = List.of(List.of(2));
        Object divider2 = List.of(List.of(6));
        List<Object> values = Stream.concat(Stream.of(divider1, divider2), this.values.stream().flatMap(pair -> pair.toList().stream()))
                .sorted(Thirteen::check)
                .collect(Util.collectReverseList());

        int sum = 1;
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).equals(divider1) || values.get(i).equals(divider2)) {
                sum *= i + 1;
            }
        }
        return Integer.toString(sum);
    }

    private static int check(Object first, Object second) {
        if (first instanceof List<?> list1 && second instanceof List<?> list2) {
            for (int i = 0; i < Math.min(list1.size(), list2.size()); i++) {
                int check = check(list1.get(i), list2.get(i));
                if (check != 0) {
                    return check;
                }
            }
            return Integer.compare(list2.size(), list1.size());
        }
        if (first instanceof Integer int1 && second instanceof Integer int2) {
            return Integer.compare(int2, int1);
        }
        return check(array(first), array(second));
    }

    private static List<?> array(Object element) {
        if (element instanceof List<?> list) return list;
        return List.of(element);
    }

    private static Pair<Object, Integer> parse(String value, int pos, char end) {
        char c = value.charAt(pos);
        if (c == '[') {
            return parseArray(value, pos);
        } else if (Character.isDigit(c)) {
            IntMatcher matcher = IntMatcher.find(Pattern.compile("-?\\d+"), value.substring(pos));
            return new Pair<>(matcher.group(), pos + matcher.end());
        } else if (c == end) {
            return new Pair<>(null, pos + 1);
        }
        throw new IllegalArgumentException("Invalid character: " + c);
    }

    private static Pair<Object, Integer> parseArray(String value, int pos) {
        List<Object> list = new ArrayList<>();
        boolean found = true;
        pos = pos + 1;
        while (true) {
            Pair<Object, Integer> output = parse(value, pos, ']');
            if (output.left() == null) return new Pair<>(list, output.right());
            if (!found) throw new IllegalStateException("Expected , but found " + value.charAt(pos));
            list.add(output.left());
            pos = output.right();
            found = value.charAt(pos) == ',';
            if (found) pos++;
        }
    }
}
