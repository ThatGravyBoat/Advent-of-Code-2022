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

    public T previous() {
        if (getIndex() < 0) {
            set(size() - 1);
        }
        return get(index--);
    }

    public T current() {
        return get(getIndex());
    }

    public void set(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public boolean isFinished() {
        return index >= size();
    }
}
