package tech.thatgravyboat.aoc.utils;

import java.util.List;

public record Pair<L, R>(L left, R right) {

    public List<Object> toList() {
        return List.of(left, right);
    }
}
