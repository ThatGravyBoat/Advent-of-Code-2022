package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;

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

    @Override
    public String partOne() {
        int score = 0;
        for (String s : getInput()) {
            String[] split = s.split(" ");
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
        List<String> input = getInput();
        int score = 0;
        for (String s : input) {
            String[] split = s.split(" ");
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
