package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.IntMatcher;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.ToLongBiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TwentyOne extends Template {

    private static final String HUMAN = "humn";
    private static final String ROOT = "root";
    private static final Pattern PATTERN = Pattern.compile("(\\w{4}): ((\\w{4}) ([+\\-*/]) (\\w{4})|\\d+)");

    public static void main(String[] args) {
        new TwentyOne().load(21);
    }

    public final Map<String, Monkey> monkeys = new HashMap<>();

    @Override
    protected void onInputLoaded() {
        for (String s : getInput()) {
            IntMatcher intLine = IntMatcher.find(PATTERN, s);
            Matcher matcher = intLine.getMatcher();
            if (matcher.group(3) == null) {
                monkeys.put(matcher.group(1), new Monkey(null, null, null, intLine.group(2)));
            } else {
                ToLongBiFunction<Long, Long> operator = switch (matcher.group(4)) {
                    case "+" -> Long::sum;
                    case "-" -> (a, b) -> a - b;
                    case "*" -> (a, b) -> a * b;
                    case "/" -> (a, b) -> a / b;
                    default -> throw new IllegalStateException("Unexpected value: " + matcher.group(4));
                };
                monkeys.put(matcher.group(1), new Monkey(operator, matcher.group(3), matcher.group(5), -1));
            }
        }
    }

    @Override
    public String partOne() {
        final Map<String, Long> values = parseInputs((id, value) -> value.value());
        return Long.toString(values.get(ROOT));
    }

    @Override
    public String partTwo() {
        final Monkey root = monkeys.get(ROOT);
        long ourNumber = monkeys.get(HUMAN).value();

        while (true) {
            final long finalValue = ourNumber;
            final Map<String, Long> values = parseInputs((id, value) -> id.equals(HUMAN) ? finalValue : value.value());

            final long difference = values.get(root.first()) - values.get(root.second());
            if (difference != 0) {
                final long check = Math.abs(difference);
                long value = 100000000000L;
                while (value != 0) {
                    if (check > value) {
                        ourNumber += (value / 10) * Long.signum(difference);
                        break;
                    }
                    value /= 10;
                }
                continue;
            }
            break;
        }
        return Long.toString(ourNumber);
    }

    private Map<String, Long> parseInputs(BiFunction<String, Monkey, Long> getter) {
        final Map<String, Long> values = new HashMap<>();
        boolean parsing = true;
        while (parsing) {
            parsing = false;
            for (Map.Entry<String, Monkey> entry : monkeys.entrySet()) {
                if (values.containsKey(entry.getKey())) continue;

                Long value = getter.apply(entry.getKey(), entry.getValue());
                if (entry.getValue().op() == null) {
                    if (value != null) {
                        values.put(entry.getKey(), value);
                        parsing = true;
                    }
                } else {
                    Long first = values.get(entry.getValue().first());
                    Long second = values.get(entry.getValue().second());
                    if (first != null && second != null) {
                        values.put(entry.getKey(), entry.getValue().op().applyAsLong(first, second));
                        parsing = true;
                    }
                }
            }
        }
        return values;
    }

    public record Monkey(ToLongBiFunction<Long, Long> op, String first, String second, long value) {}
}
