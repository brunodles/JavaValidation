package com.github.brunodles.javavalidation.matcher;

import com.github.brunodles.javavalidation.Errors;
import com.github.brunodles.retrofunctions.IntConsumer;

import static com.github.brunodles.javavalidation.matcher.Common._if;

/**
 * Created by bruno on 04/06/16.
 */
public class BooleanMatcher implements ObjectMatcher<Boolean, BooleanMatcher>,
        NotMatcher<BooleanMatcher> {

    Boolean value;
    IntConsumer adder;
    private boolean not;

    public BooleanMatcher(Boolean value, IntConsumer adder) {
        this.value = value;
        this.adder = adder;
    }

    /**
     * Check if the reference value is true
     *
     * @return the current object, to be used as a builder
     */
    public BooleanMatcher isTrue() {
        _if(() -> value, adder, Errors.TRUE, not, this::setNotFalse);
        return this;
    }

    /**
     * Check if the reference value is false
     *
     * @return the current object, to be used as a builder
     */
    public BooleanMatcher isFalse() {
        _if(() -> !value, adder, Errors.FALSE, not, this::setNotFalse);
        return this;
    }

    /**
     * Check if the reference value is null
     *
     * @return the current object, to be used as a builder
     */
    @Override
    public BooleanMatcher isNull() {
        _if(() -> value == null, adder, Errors.NULL, not, this::setNotFalse);
        return this;
    }

    /**
     * Check if the reference value is equals to expected
     *
     * @param expected another boolean
     * @return the current object, to be used as a builder
     */
    @Override
    public BooleanMatcher isEqualsTo(Boolean expected) {
        _if(() -> value.equals(expected), adder, Errors.EQUAL, not, this::setNotFalse);
        return this;
    }

    private void setNotFalse() {
        not = false;
    }

    @Override
    public BooleanMatcher not() {
        not = true;
        return this;
    }
}
