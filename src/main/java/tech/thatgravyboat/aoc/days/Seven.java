package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.IntMatcher;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Seven extends Template {

    private static final Pattern LINE_PATTERN = Pattern.compile("((?:\\$ )?[a-zA-Z0-9]+) ?([a-zA-Z0-9./]+)?");
    private static final int PART_ONE_SIZE = 100_000;
    private static final int FILE_SYSTEM_SIZE = 70_000_000;
    private static final int NEED_UPDATE_SIZE = 30_000_000;

    public static void main(String[] args) {
        new Seven().load(7);
    }

    private final Directory root = new Directory(null, new LinkedHashMap<>(), new LinkedHashMap<>());

    /**
     * Loop through the lines
     * <br>
     * Pattern match the line
     * <br>
     * Check the first group if its running change directory, list directory, if a directory was listed, or if anything happens.
     * <br>
     * If change directory then check group 2 and determine directory and set current to that directory.
     * <br>
     * If list directory then do nothing as we assume stuff later on.
     * <br>
     * If a directory was listed then check group 2 and determine directory and add it to the current directory.
     * <br>
     * If anything happens then check group 2 and determine file and add it to the current directory with group 1 as size.
     */
    @Override
    protected void onInputLoaded() {
        Directory current = root;
        for (String s : getInput()) {
            IntMatcher intLine = IntMatcher.find(LINE_PATTERN, s);
            Matcher line = intLine.getMatcher();
            switch (line.group(1)) {
                case "$ cd" -> current = switch (line.group(2)) {
                    case ".." -> current.parent;
                    case "/" -> root;
                    default -> current.directories.get(line.group(2));
                };
                case "$ ls" -> {}
                case "dir" -> current.directories.put(line.group(2), new Directory(current, new LinkedHashMap<>(), new LinkedHashMap<>()));
                default -> current.files.put(line.group(2), intLine.group(1));
            }
        }
    }

    /**
     * Stream all the directories.
     * <br>
     * Map the directories to their size.
     * <br>
     * Filter out directories that are greater than 30,000,000 in size.
     * <br>
     * Sum the remaining directories.
     */
    @Override
    public String partOne() {
        int total = root.stream()
            .mapToInt(Directory::getTotalSize)
            .filter(size -> size <= PART_ONE_SIZE)
            .sum();
        return String.valueOf(total);
    }

    /**
     * Calculate amount of space needed to update the system.
     * <br>
     * Stream through all directories mapping to its size.
     * <br>
     * Filter out directories that are less than the amount of space needed to update the system.
     * <br>
     * Reduce to the smallest directory that is needed to be deleted to update the system.
     */
    @Override
    public String partTwo() {
        final int amountNeeded = NEED_UPDATE_SIZE - (FILE_SYSTEM_SIZE - root.getTotalSize());

        int smallestDeletedDirectory = root.stream()
            .mapToInt(Directory::getTotalSize)
            .filter(size -> size >= amountNeeded)
            .reduce(Math::min)
            .orElseThrow();
        return Integer.toString(smallestDeletedDirectory);
    }

    public record Directory(Directory parent, Map<String, Directory> directories, Map<String, Integer> files) {

        public int getTotalSize() {
            return stream().mapToInt(Directory::getFileSize).sum();
        }

        public int getFileSize() {
            return files.values().stream().mapToInt(Integer::intValue).sum();
        }

        /**
         * This is important for everything we concatenate this directory and then flatmap stream all subdirectories.
         */
        public Stream<Directory> stream() {
            return Stream.concat(Stream.of(this), directories.values().stream().flatMap(Directory::stream));
        }

        @Override
        public int hashCode() {
            return Objects.hash(directories, files);
        }
    }
}
