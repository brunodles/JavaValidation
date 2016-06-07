package com.github.brunodles.javavalidation.matcher;

import com.github.brunodles.javavalidation.Errors;
import com.github.brunodles.retrofunctions.IntConsumer;

import static com.github.brunodles.javavalidation.matcher.Common._if;

/**
 * Created by bruno on 04/06/16.
 */
public class ObjectMatcherImpl<T> implements ObjectMatcher<T, ObjectMatcherImpl>,
        NotMatcher<ObjectMatcherImpl> {

    private T value;
    private IntConsumer adder;
    private boolean not;

    public ObjectMatcherImpl(T value, IntConsumer adder) {
        this.value = value;
        this.adder = adder;
    }

    @Override
    public ObjectMatcherImpl isNull() {
        _if(() -> value == null, adder, Errors.NULL, not, this::setNotFalse);
        return this;
    }

    private void setNotFalse() {
        not = false;
    }

    @Override
    public ObjectMatcherImpl isEqualsTo(T expected) {
        _if(() -> value.equals(expected), adder, Errors.EQUAL, not, this::setNotFalse);
        return this;
    }

    @Override
    public ObjectMatcherImpl not() {
        not = true;
        return this;
    }
}
