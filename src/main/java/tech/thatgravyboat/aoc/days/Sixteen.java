package tech.thatgravyboat.aoc.days;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.Util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sixteen extends Template {

    private static final Pattern PATTERN = Pattern.compile("Valve (\\w\\w) has flow rate=(\\d+); tunnels? leads? to valves? ((?:\\w\\w(?:, )?)+)+");

    public static void main(String[] args) {
        new Sixteen().load(16);
    }

    private final Map<String, Valve> values = new HashMap<>();

    @Override
    protected void onInputLoaded() {
        Util.findInt(PATTERN, getInput()).forEach(match -> {
            Matcher matcher = match.getMatcher();
            values.put(matcher.group(1), new Valve(matcher.group(1), match.group(2), List.of(matcher.group(3).split(", "))));
        });
    }

    @Override
    public String partOne() {
        Stack<State> states = Util.newStack(List.of(new State(values.get("AA"))));
        Object2IntMap<PathNote> viewed = new Object2IntOpenHashMap<>();
        viewed.defaultReturnValue(Integer.MIN_VALUE);
        List<State> finished = new ArrayList<>();

        while (!states.isEmpty()) {
            State state = states.pop();
            Valve valve = values.get(state.id());
            int time = state.time() + 1;
            int flow = valve.flow();

            PathNote note = state.note();

            // If we've already seen this path at this time, and it's not better, skip it
            if (viewed.getInt(note) >= state.flow()) {
                continue;
            }
            viewed.put(note, state.flow());


            // If we've reached the end, add it to the finished list
            if (time == 30) {
                finished.add(state);
                continue;
            }

            int totalFlow = state.flow() + state.opened().stream()
                    .map(values::get)
                    .mapToInt(Valve::flow)
                    .sum();

            if (valve.flow() != 0 && !state.opened().contains(state.id())) {
                states.add(new State(
                        time,
                        state.id(),
                        totalFlow + flow,
                        Util.copyAndAdd(state.opened(), state.id())
                ));
            }

            for (String tunnel : valve.tunnels()) {
                states.add(new State(time, tunnel, totalFlow, state.opened()));
            }
        }

        return Integer.toString(finished.stream()
                .mapToInt(State::flow)
                .max()
                .orElseThrow());
        //Works for example but not actual input, IDK what's wrong.
    }

    @Override
    public String partTwo() {
        return null;
    }

    public record Valve(String id, int flow, List<String> tunnels) {
    }

    public record State(int time, String id, int flow, Set<String> opened) {

        public State(Valve valve) {
            this(0, valve.id(), valve.flow(), new HashSet<>());
        }

        public PathNote note() {
            return new PathNote(time(), id());
        }
    }

    public record PathNote(int time, String id) {
    }
}
