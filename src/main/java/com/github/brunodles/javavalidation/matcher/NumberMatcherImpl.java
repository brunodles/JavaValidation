package com.github.brunodles.javavalidation.matcher;

import com.github.brunodles.javavalidation.Errors;
import com.github.brunodles.retrofunctions.BooleanSupplier;
import com.github.brunodles.retrofunctions.IntConsumer;

import static com.github.brunodles.javavalidation.matcher.Common._if;


/**
 * Created by bruno on 03/06/16.
 */
class NumberMatcherImpl<T extends Number & Comparable,
        SubClass extends NumberMatcherImpl>
        implements NumberMatcher<T, SubClass>, ObjectMatcher<T, SubClass> {
    private T value;
    private IntConsumer adder;
    private boolean not;

    NumberMatcherImpl(T value, IntConsumer adder) {
        this.value = value;
        this.adder = adder;
    }

    @Override
    public SubClass isEqualsTo(T expected) {
        _if(() -> value.equals(expected), adder, Errors.EQUAL, not, this::setNotFalse);
        return (SubClass) this;
    }

    private void setNotFalse() {
        not = false;
    }

    @Override
    public SubClass isGreater(T expected) {
        _if(() -> gt(expected), adder, Errors.GREATER, not, this::setNotFalse);
        return (SubClass) this;
    }

    @Override
    public SubClass isLower(T expected) {
        _if(() -> lt(expected), adder, Errors.LOWER, not, this::setNotFalse);
        return (SubClass) this;
    }

    @Override
    public SubClass isBetween(T min, T max) {
        _if(() -> bt(min, max), adder, Errors.BETWEEN, not, this::setNotFalse);
        return (SubClass) this;
    }

    public boolean gt(T expected) {
        return value.compareTo(expected) > 0;
    }

    public boolean lt(T expected) {
        return value.compareTo(expected) < 0;
    }

    private boolean bt(T min, T max) {
        return gt(min) && lt(max);
    }

    @Override
    public SubClass isNull() {
        _if(() -> value == null, adder, Errors.NULL, not, this::setNotFalse);
        return (SubClass) this;
    }

    @Override
    public SubClass not() {
        not = true;
        return (SubClass) this;
    }
}
