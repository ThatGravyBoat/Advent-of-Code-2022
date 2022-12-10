package tech.thatgravyboat.aoc.utils;

public class ConsoleOutput {

    private final StringBuilder output = new StringBuilder();

    public ConsoleOutput append(String s, Color foreground, Color background) {
        output.append(foreground.foreground).append(background.background).append(s);
        return this;
    }

    public ConsoleOutput appendLine() {
        output.append(Color.RESET.foreground).append(Color.RESET.background).append("\n");
        return this;
    }

    public String getOutput() {
        return output + Color.RESET.foreground + Color.RESET.background;
    }

    public void clear() {
        output.setLength(0);
    }

    public enum Color {
        RESET("\u001B[0m", "\u001B[49m"),
        BLACK("\u001B[30m", "\u001B[40m"),
        RED("\u001B[31m", "\u001B[41m"),
        GREEN("\u001B[32m", "\u001B[42m"),
        YELLOW("\u001B[33m", "\u001B[43m"),
        BLUE("\u001B[34m", "\u001B[44m"),
        PURPLE("\u001B[35m", "\u001B[45m"),
        CYAN("\u001B[36m", "\u001B[46m"),
        WHITE("\u001B[37m", "\u001B[47m");

        public final String foreground;
        public final String background;

        Color(String foreground, String background) {
            this.foreground = foreground;
            this.background = background;
        }
    }
}
