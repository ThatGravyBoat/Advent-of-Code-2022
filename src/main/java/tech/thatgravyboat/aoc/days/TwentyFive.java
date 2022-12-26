package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;

public class TwentyFive extends Template {

    public static void main(String[] args) {
        new TwentyFive().load(25);
    }

    @Override
    public String partOne() {
        return toSNAFU(getInput().stream().mapToLong(TwentyFive::fromSNAFU).sum());
    }

    @Override
    public String partTwo() {
        return null;
    }

    private static long fromSNAFU(String input) {
        long value = 0;
        for (char c : input.toCharArray()) {
            value *= 5;
            switch (c) {
                case '=' -> value -= 2;
                case '-' -> value -= 1;
                case '0' -> value += 0;
                case '1' -> value += 1;
                case '2' -> value += 2;
            }
        }
        return value;
    }

    public static String toSNAFU(long input) {
        StringBuilder output = new StringBuilder();
        while (input != 0) {
            final int value = Math.toIntExact(input % 5);
            input = switch (value) {
                case 3, 4 -> {
                    output.append(value == 4 ? '-' : '=');
                    yield (input / 5) + 1;
                }
                default -> {
                    output.append((char) (value + '0'));
                    yield input / 5;
                }
            };
        }
        return output.reverse().toString();
    }
}

