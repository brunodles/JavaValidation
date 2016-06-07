package com.github.brunodles.javavalidation.matcher;

/**
 * Created by bruno on 03/06/16.
 */
public interface NumberMatcher<T extends Number, SubClass extends NumberMatcher>
        extends EqualsMatcher<T, SubClass>, NotMatcher<SubClass> {

    SubClass isGreater(T expected);

    SubClass isLower(T expected);

    SubClass isBetween(T min, T max);

}
