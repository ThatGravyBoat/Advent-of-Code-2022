package tech.thatgravyboat.aoc.utils.bitint;

import java.math.BigInteger;

@FunctionalInterface
public interface BigInt2BigIntFunction {

    BigInteger apply(BigInteger value);
}