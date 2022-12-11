package tech.thatgravyboat.aoc.utils.bitint;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

public class BigIntList extends ArrayList<BigInteger> {

    public BigIntList(Collection<? extends BigInteger> c) {
        super(c);
    }

    public void add(int integer) {
        add(BigInteger.valueOf(integer));
    }
}
