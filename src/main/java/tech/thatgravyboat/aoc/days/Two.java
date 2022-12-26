package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Two extends Template {

    private static final Pattern PATTERN = Pattern.compile("([A-Z]) ([A-Z])");

    private static final int ROCK = 1;
    private static final int PAPER = 2;
    private static final int SCISSORS = 3;

    private static final int WIN = 6;
    private static final int DRAW = 3;
    private static final int LOSE = 0;

    public static void main(String[] args) {
        new Two().load(2);
    }

    private final List<Move> moves = new ArrayList<>();

    /**
     * Loop through the input and split the moves into an array of 2 strings with
     * the first being the players move and the second being our input.
     */
    @Override
    protected void onInputLoaded() {
        moves.addAll(Util.find(PATTERN, getInput(), matcher -> new Move(matcher.group(1), matcher.group(2))));
    }


    @Override
    public String partOne() {
        int score = 0;
        for (Move split : moves) {
            score += switch (split.opponent()) {
                case "A" -> switch (split.other()) {
                    case "X" -> ROCK + DRAW;
                    case "Y" -> PAPER + WIN;
                    case "Z" -> SCISSORS + LOSE;
                    default -> 0;
                };
                case "B" -> switch (split.other()) {
                    case "X" -> ROCK + LOSE;
                    case "Y" -> PAPER + DRAW;
                    case "Z" -> SCISSORS + WIN;
                    default -> 0;
                };
                case "C" -> switch (split.other()) {
                    case "X" -> ROCK + WIN;
                    case "Y" -> PAPER + LOSE;
                    case "Z" -> SCISSORS + DRAW;
                    default -> 0;
                };
                default -> 0;
            };
        }

        return Integer.toString(score);
    }

    @Override
    public String partTwo() {
        int score = 0;
        for (Move split : moves) {
            score += switch (split.opponent()) {
                case "A" -> switch (split.other()) {
                    case "X" -> SCISSORS + LOSE;
                    case "Y" -> ROCK + DRAW;
                    case "Z" -> PAPER + WIN;
                    default -> 0;
                };
                case "B" -> switch (split.other()) {
                    case "X" -> ROCK + LOSE;
                    case "Y" -> PAPER + DRAW;
                    case "Z" -> SCISSORS + WIN;
                    default -> 0;
                };
                case "C" -> switch (split.other()) {
                    case "X" -> PAPER + LOSE;
                    case "Y" -> SCISSORS + DRAW;
                    case "Z" -> ROCK + WIN;
                    default -> 0;
                };
                default -> 0;
            };
        }

        return Integer.toString(score);
    }

    public record Move(String opponent, String other) {

    }
}
