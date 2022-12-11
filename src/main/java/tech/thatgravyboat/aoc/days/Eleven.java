package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.IntMatcher;
import tech.thatgravyboat.aoc.utils.Util;
import tech.thatgravyboat.aoc.utils.bitint.BigInt2BigIntFunction;
import tech.thatgravyboat.aoc.utils.bitint.BigIntList;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Eleven extends Template {

    private static final Pattern PATTERN = Pattern.compile("Monkey \\d:\\n {2}Starting items: ((?:\\d+(?:, )?)+)\\n {2}Operation: new = old ([+*]) (\\d+|old)\\n {2}Test: divisible by (\\d+)\\n {4}If true: throw to monkey (\\d)\\n {4}If false: throw to monkey (\\d)");

    public static void main(String[] args) {
        new Eleven().load(11);
    }

    @Override
    public String partOne() {
        return solve(20, new BigInteger("3"));
    }

    @Override
    public String partTwo() {
        return solve(10000, BigInteger.ONE);
    }

    private String solve(int rounds, BigInteger divisor) {
        var monkeys = getMonkeys();
        BigInteger lcm = monkeys.stream()
            .map(Monkey::modulo)
            .reduce(BigInteger.valueOf(1), (a, b) -> a.multiply(b.divide(a.gcd(b))));

        Util.repeat(rounds, () -> {
            for (Monkey monkey : monkeys) {
                monkey.items().stream()
                    .map(monkey.operation::apply)
                    .map(val -> val.mod(lcm))
                    .map(val -> val.divide(divisor))
                    .forEachOrdered(value -> monkey.addToNext(monkeys, value));
                monkey.items().clear();
            }
        });

        List<Long> list = monkeys.stream()
            .map(Monkey::interactions)
            .map(AtomicLong::get)
            .sorted()
            .collect(Util.collectReverseList());
        return Long.toString(list.get(0) * list.get(1));
    }

    public record Monkey(BigIntList items, BigInt2BigIntFunction operation, BigInteger modulo, int monkey1, int monkey2, AtomicLong interactions) {

        public void addToNext(List<Monkey> monkeys, BigInteger value) {
            monkeys.get(value.mod(modulo()).intValue() == 0 ? monkey1() : monkey2()).items().add(value);
            interactions.incrementAndGet();
        }
    }

    private List<Monkey> getMonkeys() {
        List<String> input = Util.groupLists(getInput(), 7).stream().map(s -> String.join("\n", s)).toList();
        List<Monkey> monkeys = new ArrayList<>();

        for (String data : input) {
            IntMatcher intMatcher = IntMatcher.find(PATTERN, data);
            Matcher matcher = intMatcher.getMatcher();

            BigIntList items = new BigIntList(Arrays.stream(matcher.group(1).split(", ")).map(Integer::parseInt).map(BigInteger::valueOf).toList());
            Optional<BigInteger> value = Optional.ofNullable(matcher.group(3).equals("old") ? null : BigInteger.valueOf(intMatcher.group(3)));
            BigInt2BigIntFunction operation = switch (matcher.group(2)) {
                case "+" -> old -> old.add(value.orElse(old));
                case "*" -> old -> old.multiply(value.orElse(old));
                default -> throw new IllegalStateException("Unexpected value: " + matcher.group(2));
            };

            monkeys.add(new Monkey(
                items,
                operation,
                BigInteger.valueOf(intMatcher.group(4)),
                intMatcher.group(5),
                intMatcher.group(6),
                new AtomicLong()
            ));
        }
        return monkeys;
    }

}
