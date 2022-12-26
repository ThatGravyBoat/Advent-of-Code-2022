package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.ConsoleOutput;
import tech.thatgravyboat.aoc.utils.IntMatcher;
import tech.thatgravyboat.aoc.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

public class Ten extends Template {

    private static final Pattern PATTERN = Pattern.compile("(\\w+) ?(-?\\d+)?");

    public static void main(String[] args) {
        new Ten().load(10);
    }

    /**
     * Run instructions.
     * <br>
     * Check if cycle - 20 is divisible by 40, if so, add the value times cycle to the signal.
     */
    @Override
    public String partOne() {
        AtomicInteger signal = new AtomicInteger();
        runInstructions((cycle, x) -> {
            if (((cycle - 20) % 40) == 0) {
                signal.addAndGet(x * cycle);
            }
        });
        return signal.toString();
    }

    /**
     * Reset stack.
     * <br>
     * Run instructions.
     * <br>
     * Get current cycle and value.
     * <br>
     * Calculate position and then check if abs(pos - x) is less then or equal to 1 then print a square.
     * <br>
     * If cycle is divisible by 40 then print a new line.
     */
    @Override
    public String partTwo() {
        onInputLoaded();
        ConsoleOutput output = new ConsoleOutput();
        output.appendLine();
        runInstructions((cycle, x) -> {
            int pos = (cycle - 1) % 40;
            output.append(" ", ConsoleOutput.Color.RESET, Math.abs(pos - x) <= 1 ? ConsoleOutput.Color.WHITE : ConsoleOutput.Color.RESET);
            if (cycle % 40 == 0) output.appendLine();
        });
        return output.getOutput();
    }

    public Stack<Instruction> getInstructions() {
        List<Instruction> instructions = new ArrayList<>();
        for (String s : getInput()) {
            IntMatcher intLine = IntMatcher.find(PATTERN, s);
            switch (intLine.getMatcher().group(1)) {
                case "addx" ->
                        instructions.add(new Instruction(OperationType.ADDX, intLine.group(2), new AtomicInteger(2)));
                case "noop" -> instructions.add(new Instruction(OperationType.NOOP, 0, new AtomicInteger(1)));
                default -> throw new IllegalStateException("Unexpected value");
            }
        }
        Collections.reverse(instructions);
        return Util.newStack(instructions);
    }

    /**
     * Loop through the instructions
     * <br>
     * Check if the current instruction is finished if it is then check if addx and then add value and then pop
     * <br>
     * If instruction is null then pop an instruction from the stack
     * <br>
     * Tick the instruction
     * <br>
     * Run the consumer with the cycle and the value
     * <br>
     * Increment the cycle
     */
    private void runInstructions(BiConsumer<Integer, Integer> consumer) {
        Stack<Instruction> instructions = getInstructions();
        int x = 1;
        int cycle = 1;

        Instruction instruction = null;
        while (!instructions.isEmpty()) {
            if (instruction != null && instruction.finished()) {
                if (instruction.type == OperationType.ADDX) {
                    x += instruction.value();
                }
                instruction = null;
                if (instructions.isEmpty()) {
                    break;
                }
            }
            if (instruction == null) {
                instruction = instructions.pop();
            }
            instruction.tick();
            consumer.accept(cycle, x);
            cycle++;
        }
    }

    public enum OperationType {
        ADDX,
        NOOP
    }

    public record Instruction(OperationType type, int value, AtomicInteger ticks) {

        public void tick() {
            ticks.decrementAndGet();
        }

        public boolean finished() {
            return ticks.get() <= 0;
        }
    }
}
