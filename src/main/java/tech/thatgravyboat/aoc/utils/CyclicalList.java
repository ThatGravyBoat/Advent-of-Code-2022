package tech.thatgravyboat.aoc.utils;

import java.util.ArrayList;
import java.util.Collection;

public class CyclicalList<T> extends ArrayList<T> {

    private int index = 0;

    public CyclicalList(Collection<? extends T> c) {
        super(c);
    }

    public T next() {
        if (getIndex() >= size()) {
            set(0);
        }
        return get(index++);
    }

    public void set(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
