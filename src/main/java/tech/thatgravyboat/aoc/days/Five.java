package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Five extends Template {

    public static void main(String[] args) {
        new Five().load(5);
    }

    private final List<Move> moves = new ArrayList<>();

    @Override
    protected void onInputLoaded() {
        for (String s : getInput()) {
            if (!s.startsWith("move")) continue;
            String[] split = s.replaceAll("move ", "")
                    .replace("from ", "")
                    .replace("to ", "")
                    .split(" ");
            moves.add(new Move(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
        }
    }

    @Override
    public String partOne() {
        List<Stack<Character>> stacks = getInitialStack();
        moves.forEach(move -> IntStream.range(0, move.amount).forEachOrdered(i -> move.getTo(stacks).push(move.getFrom(stacks).pop())));
        return stacks.stream()
            .map(Stack::pop)
            .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
            .toString();
    }

    @Override
    public String partTwo() {
        List<Stack<Character>> stacks = getInitialStack();
        moves.forEach(move -> IntStream.range(0, move.amount)
            .mapToObj(i -> move.getFrom(stacks).pop())
            .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                Collections.reverse(list);
                return list;
            }))
            .forEach(c -> move.getTo(stacks).push(c))
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

        public <T> Stack<T> getFrom(List<Stack<T>> stacks) {
            return stacks.get(from - 1);
        }

        public <T> Stack<T> getTo(List<Stack<T>> stacks) {
            return stacks.get(to - 1);
        }
    }
}
