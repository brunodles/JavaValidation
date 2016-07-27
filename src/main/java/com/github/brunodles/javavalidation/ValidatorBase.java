package com.github.brunodles.javavalidation;

import com.github.brunodles.javavalidation.matcher.When;
import com.github.brunodles.retrofunctions.IntConsumer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * This class is intent to be used to simplify the implementation of the Validator.
 * To use it you just need to implement the method validate.
 * <p>
 * Created by bruno on 03/06/16.
 */
public abstract class ValidatorBase<T> implements Validator<T> {

    private HashMap<String, Integer> errorMap;
    private T mObjectToValidate;

    public ValidatorBase(T objectToValidate) {
        errorMap = new HashMap<>();
        this.mObjectToValidate = objectToValidate;
    }

    public Validator validate() {
        validate(mObjectToValidate);
        return this;
    }

    protected abstract void validate(T object);

    /**
     * With this method you can get the errors from one key
     *
     * @param key
     * @return
     */
    private Integer errorsFrom(String key) {
        Integer errors = errorMap.get(key);
        if (errors == null) errors = 0;
        return errors;
    }

    /**
     * Check if the object is valid
     *
     * @return true if the object don't have any error
     */
    public boolean isValid() {
        validate();
        return errorMap.isEmpty();
    }

    /**
     * With this you can check if one key have certain error.
     *
     * @param key   the same key used on {@link #add}
     * @param error the error from {@link Errors}
     * @return true if the key contains the error
     */
    @Override
    public boolean contains(String key, int error) {
        Integer errors = errorsFrom(key);
        return (errors & error) == error;
    }

    @Override
    public boolean contains(String key) {
        return errorsFrom(key) != 0;
    }

    @Override
    public List<String> keys() {
        return Collections.unmodifiableList(new ArrayList<>(errorMap.keySet()));
    }

    /**
     * With this you can use a builder to create all errors for one key
     *
     * @param key the key where the errors will be added
     * @return a helper to build the errors {@link When}
     */
    public When addTo(String key) {
        return new When(adder(key));
    }

    /**
     * Use this method to add an error.
     *
     * @param key   This key will be used to know what is wrong, you ca use the field name here.
     * @param error All errors are constants, use one of {@link Errors}
     */
    private void add(String key, int error) {
        Integer errors = errorsFrom(key);
        errors |= error;
        errorMap.put(key, errors);
    }

    /**
     * This will return one error adder for the key
     *
     * @param key the key you want to add error
     * @return a {@link IntConsumer}, to add errors on the key just call {@link IntConsumer#accept(int)}
     */
    private IntConsumer adder(String key) {
        return error -> add(key, error);
    }
}
