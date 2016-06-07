package com.github.brunodles.javavalidation.matcher;

import com.github.brunodles.retrofunctions.BooleanSupplier;
import com.github.brunodles.retrofunctions.IntConsumer;

/**
 * Created by bruno on 03/06/16.
 */
class Common {

    private static void _if(BooleanSupplier condition, IntConsumer block, int error) {
        try {
            if (condition.getAsBoolean()) block.accept(error);
        } catch (Exception e) {
        }
    }

    static void _if(BooleanSupplier condition, IntConsumer block, int error,
                           boolean not, Runnable cleanNot) {
        if (not) {
            Common._if(() -> !condition.getAsBoolean(), block, error * -1);
            cleanNot.run();
        } else {
            Common._if(condition, block, error);
        }
    }
}
