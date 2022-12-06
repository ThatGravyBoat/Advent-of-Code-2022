package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.IntMatcher;
import tech.thatgravyboat.aoc.utils.StringUtils;
import tech.thatgravyboat.aoc.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Five extends Template {

    public static void main(String[] args) {
        new Five().load(5);
    }

    private static final Pattern MOVE_PATTERN = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

    private final List<Move> moves = new ArrayList<>();

    /**
     * Loop through the input
     * <br>
     * Match each line with the pattern
     * <br>
     * If not found, skip
     * <br>
     * If found create a new move object and add it to the list
     */
    @Override
    protected void onInputLoaded() {
        for (String s : getInput()) {
            IntMatcher matcher = new IntMatcher(MOVE_PATTERN, s);
            if (!matcher.find()) continue;
            moves.add(new Move(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
    }

    /**
     * Get the initial stacks.
     * <br>
     * Loop through the moves, and for each amount in a move pop from one stack and push to another.
     * <br>
     * Stream the stacks popping the top of each stack and collecting to a String.
     * <br>
     * Return the String.
     */
    @Override
    public String partOne() {
        List<Stack<Character>> stacks = getInitialStack();
        moves.forEach(move -> Util.repeat(move.amount, () -> move.push(stacks, move.pop(stacks))));
        return stacks.stream()
            .map(Stack::pop)
            .collect(StringUtils.collectToString())
            .toString();
    }

    /**
     * Get the initial stacks.
     * <br>
     * Loop through the moves.
     * <br>
     * For each amount in a move, pop the top of the stack and collect to a list.
     * <br>
     * Reverse the list, and push the items back onto the other stack.
     * <br>
     * Stream the stacks and pop the top of each stack.
     * <br>
     * Collect the characters to a string.
     * <br>
     * Return the string.
     */
    @Override
    public String partTwo() {
        List<Stack<Character>> stacks = getInitialStack();
        moves.forEach(move -> IntStream.range(0, move.amount)
            .mapToObj(i -> move.pop(stacks))
            .collect(Util.collectReverseList())
            .forEach(c -> move.push(stacks, c))
        );
        return stacks.stream()
                .map(Stack::pop)
                .collect(StringUtils.collectToString())
                .toString();
    }

    /**
     * Create a list of 9 lists.
     * <br>
     * Loop through the input, if first char is a space then break because that means were done with the stack data.
     * <br>
     * Split the input every 4 characters.
     * <br>
     * Loop through the split input, removing all chars that are not A-Z.
     * <br>
     * If blank then continue, else add the char to the list.
     * <br>
     * Stream through the lists and reverse them.
     * <br>
     * Map each list to a stack.
     * <br>
     * Collect the stacks to a list.
     * <br>
     * Return the list.
     */
    private List<Stack<Character>> getInitialStack() {
        List<List<Character>> stacks = Util.listsWithSize(9);
        for (String s : getInput()) {
            if (Character.isSpaceChar(s.charAt(0))) break;
            String[] out = StringUtils.splitEveryX(s, 4);
            for (int i = 0; i < out.length; i++) {
                String line = out[i].replaceAll("[^A-Z]", "");
                if (line.isBlank()) continue;
                stacks.get(i).add(line.charAt(0));
            }
        }
        return stacks.stream()
                .peek(Collections::reverse)
                .map(Util::newStack)
                .toList();
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
