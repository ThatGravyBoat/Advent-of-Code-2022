package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

public class Nineteen extends Template {

    private static final Pattern PATTERN = Pattern.compile("Blueprint \\d+: Each ore robot costs (\\d) ore. Each clay robot costs (\\d) ore. Each obsidian robot costs (\\d) ore and (\\d+) clay. Each geode robot costs (\\d) ore and (\\d+) obsidian.");

    public static void main(String[] args) {
        new Nineteen().load(19);
    }

    private final List<Blueprint> blueprints = new ArrayList<>();

    @Override
    protected void onInputLoaded() {
        Util.findInt(PATTERN, getInput()).forEach(match ->
                blueprints.add(new Blueprint(
                        match.group(1),
                        match.group(2),
                        match.group(3), match.group(4),
                        match.group(5), match.group(6)
                ))
        );
    }

    @Override
    public String partOne() {
        return Integer.toString(Util.sum(results(blueprints, 24, (id, i) -> id * i)));
    }

    @Override
    public String partTwo() {
        return Integer.toString(Util.product(results(blueprints.subList(0, 3), 32, (id, i) -> i)));
    }

    public int[] results(List<Blueprint> blueprints, int time, BiFunction<Integer, Integer, Integer> operation) {
        final int[] results = new int[blueprints.size()];
        for (int i = 0; i < blueprints.size(); i++) {
            final Blueprint blueprint = blueprints.get(i);
            int max = -1;
            for (int j = 0; j < 1000000; j++) {
                max = Math.max(random(blueprint, time), max);
            }
            results[i] = operation.apply(i + 1, max);
        }
        return results;
    }

    public int random(Blueprint blueprint, int time) {
        final float decrement = 1f / 3f; // Because there are 3 robots besides geodes we just do 1/robots
        final int[] materials = new int[]{0, 0, 0, 0}; // Ore, Clay, Obsidian, Geodes
        final int[] robots = new int[]{1, 0, 0, 0}; // Ore, Clay, Obsidian, Geodes

        for (int i = 0; i < time; i++) {
            // We copy the materials, so we can update them without affecting the value check
            final int[] copy = Arrays.copyOf(materials, materials.length);

            for (int j = 0; j < robots.length; j++) {
                // Add the materials the robots have generated,
                // this is why we do the copy above because if not then the creation of robots would be wrong because it would have materials the robots just made.
                materials[j] += robots[j];
            }

            float chance = 4f / 3f; // Start at 4/3
            for (int j = blueprint.costs.length - 1; j >= 0; j--) { // Start with geodes and work our way down
                Cost cost = blueprint.costs[j];
                if (Util.chance(chance) && cost.checkAndDeduct(copy, materials)) { // If we have enough materials and we pass the chance
                    robots[j]++; // Add a robot
                    break; // Break out of the loop if a decision was made
                }
                chance -= decrement; // Decrement the chance so the next robot has a lower chance
            }
        }
        return materials[3];
    }

    public record Blueprint(Cost[] costs) {

        public Blueprint(int ore, int clay, int obsidianOre, int obsidianClay, int geodeOre, int geodeObsidian) {
            this(new Cost[]{
                    new Cost(new int[]{ore, 0, 0, 0}),
                    new Cost(new int[]{clay, 0, 0, 0}),
                    new Cost(new int[]{obsidianOre, obsidianClay, 0, 0}),
                    new Cost(new int[]{geodeOre, 0, geodeObsidian, 0})
            });
        }
    }

    public record Cost(int[] costs) {

        public boolean checkAndDeduct(int[] copy, int[] materials) {
            for (int i = 0; i < costs.length; i++) {
                if (costs[i] > copy[i]) {
                    return false;
                }
            }
            for (int i = 0; i < materials.length; i++) {
                materials[i] -= costs[i];
            }
            return true;
        }
    }
}
