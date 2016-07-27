package com.github.brunodles.javavalidation;

import java.util.List;

/**
 * Used to validate some object
 * Created by bruno on 03/06/16.
 */
public interface Validator<T> {

    /**
     * Validates one object
     *
     * @return a {@link Validator}, containing the errors
     */
    Validator validate();

    /**
     * This is the return of the whole tests.
     *
     * @return true if didn't found any error
     */
    boolean isValid();

    /**
     * With this you can check if one key have certain error.
     *
     * @param key   normaly is used the field name
     * @param error the error from {@link Errors}
     * @return true if the key contains the error
     */
    boolean contains(String key, int error);

    /**
     * With this you can check if the key have a error
     *
     * @param key normaly is used the field name
     * @return true if the key contains a error
     */
    boolean contains(String key);

    /**
     * All keys that have errors
     *
     * @return a list with all keys
     */
    List<String> keys();

}
