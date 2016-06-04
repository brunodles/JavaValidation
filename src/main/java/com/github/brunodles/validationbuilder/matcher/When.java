package com.github.brunodles.validationbuilder.matcher;

import java.util.function.IntConsumer;

/**
 * Created by bruno on 03/06/16.
 */
public class When {

    private IntConsumer adder;

    public When(IntConsumer adder) {
        this.adder = adder;
    }

    public StringMatcher when(String s) {
        return new StringMatcher(s, adder);
    }

    public IntegerMatcher when(Integer i) {
        return new IntegerMatcher(i, adder);
    }

    public <T> ObjectMatcher<T, ?> when(final T object) {
        return new ObjectMatcherImpl<T>(object, adder);
    }
}
