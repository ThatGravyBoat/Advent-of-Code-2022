package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;

import java.util.ArrayList;
import java.util.List;

public class Two extends Template {

    private static final int ROCK = 1;
    private static final int PAPER = 2;
    private static final int SCISSORS = 3;

    private static final int WIN = 6;
    private static final int DRAW = 3;
    private static final int LOSE = 0;

    public static void main(String[] args) {
        new Two().load(2);
    }

    private final List<String[]> moves = new ArrayList<>();

    /**
     * Loop through the input and split the moves into an array of 2 strings with
     * the first being the players move and the second being our input.
     */
    @Override
    public void loadData(List<String> input) {
        for (String s : input) {
            moves.add(s.split(" "));
        }
    }

    @Override
    public String partOne() {
        int score = 0;
        for (String[] split : moves) {
            score += switch (split[0]) {
                case "A" -> switch (split[1]) {
                    case "X" -> ROCK + DRAW;
                    case "Y" -> PAPER + WIN;
                    case "Z" -> SCISSORS + LOSE;
                    default -> 0;
                };
                case "B" -> switch (split[1]) {
                    case "X" -> ROCK + LOSE;
                    case "Y" -> PAPER + DRAW;
                    case "Z" -> SCISSORS + WIN;
                    default -> 0;
                };
                case "C" -> switch (split[1]) {
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
        for (String[] split : moves) {
            score += switch (split[0]) {
                case "A" -> switch (split[1]) {
                    case "X" -> SCISSORS + LOSE;
                    case "Y" -> ROCK + DRAW;
                    case "Z" -> PAPER + WIN;
                    default -> 0;
                };
                case "B" -> switch (split[1]) {
                    case "X" -> ROCK + LOSE;
                    case "Y" -> PAPER + DRAW;
                    case "Z" -> SCISSORS + WIN;
                    default -> 0;
                };
                case "C" -> switch (split[1]) {
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
}
