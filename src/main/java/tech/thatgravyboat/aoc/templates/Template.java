package tech.thatgravyboat.aoc.templates;

import java.util.List;

public abstract class Template {

    private List<String> input;

    public void loadData(List<String> input) {
        this.input = input;
        onInputLoaded();
    }

    public List<String> getInput() {
        return input;
    }

    protected void onInputLoaded() {
        // Override this method to do something when the input is loaded
    }

    protected abstract String partOne();

    protected abstract String partTwo();

    protected void load(int day) {
        TemplateLoader.load(day, this);
    }
}
