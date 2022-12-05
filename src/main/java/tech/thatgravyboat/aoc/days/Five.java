package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Five extends Template {

    public static void main(String[] args) {
        new Five().load(5);
    }

    private static final Pattern MOVE_PATTERN = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

    private final List<Move> moves = new ArrayList<>();

    @Override
    protected void onInputLoaded() {
        for (String s : getInput()) {
            Matcher matcher = MOVE_PATTERN.matcher(s);
            if (!matcher.find()) continue;
            moves.add(new Move(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3))));
        }
    }

    @Override
    public String partOne() {
        List<Stack<Character>> stacks = getInitialStack();
        moves.forEach(move -> IntStream.range(0, move.amount).forEachOrdered(i -> move.push(stacks, move.pop(stacks))));
        return stacks.stream()
            .map(Stack::pop)
            .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
            .toString();
    }

    @Override
    public String partTwo() {
        List<Stack<Character>> stacks = getInitialStack();
        moves.forEach(move -> IntStream.range(0, move.amount)
            .mapToObj(i -> move.pop(stacks))
            .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                Collections.reverse(list);
                return list;
            }))
            .forEach(c -> move.push(stacks, c))
        );
        return stacks.stream()
                .map(Stack::pop)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    private List<Stack<Character>> getInitialStack() {
        List<ArrayList<Character>> stacks = IntStream.range(0, 9)
            .mapToObj(i -> new ArrayList<Character>())
            .toList();
        for (String s : getInput()) {
            if (Character.isSpaceChar(s.charAt(0))) break;
            String[] out = splitEveryX(s);
            for (int i = 0; i < out.length; i++) {
                String line = out[i].replaceAll("[^A-Z]", "");
                if (line.isBlank()) continue;
                stacks.get(i).add(line.charAt(0));
            }
        }
        return stacks.stream()
            .peek(Collections::reverse)
            .map(list -> {
                Stack<Character> stack = new Stack<>();
                stack.addAll(list);
                return stack;
            })
            .toList();
    }

    private String[] splitEveryX(String input) {
        int size = (input.length() + 3) / 4;
        String[] output = new String[size];
        for (int i = 0; i < size; i++) {
            output[i] = input.substring(i * 4, Math.min(input.length(), (i + 1) * 4));
        }
        return output;
    }

    public record Move(int amount, int from, int to) {

        public <T> T pop(List<Stack<T>> stacks) {
            return stacks.get(from - 1).pop();
        }

        public <T> void push(List<Stack<T>> stacks, T object) {
            stacks.get(to - 1).push(object);
        }
    }
}