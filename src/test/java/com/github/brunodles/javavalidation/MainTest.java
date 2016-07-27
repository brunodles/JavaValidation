package com.github.brunodles.javavalidation;

import com.mscharhag.oleaster.runner.OleasterRunner;
import org.junit.runner.RunWith;

import static com.github.brunodles.javavalidation.Errors.*;
import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.*;

/**
 * Created by bruno on 03/06/16.
 */
@RunWith(OleasterRunner.class)
public class MainTest {

    private Validator validator;

    {
        describe("Given a Validator", () -> {
            before(() -> {
                validator = new SampleValidator(null);
            });

            describe("when pass a null value", () -> {
                it("should throw NullPointerException", () -> {
                    expect(() -> validator.validate()).toThrow(NullPointerException.class);
                });
            });

            describe("when validate a String field", () -> {

                describe("when pass a object with null values", () -> {
                    before(() -> {
                        validator = new SampleValidator(new SampleClass())
                                .validate();
                    });
                    it("should be invalid", () -> {
                        expect(validator.isValid()).toBeFalse();
                    });
                    it("should add NULL name errors", () -> {
                        expect(validator.contains("name", NULL)).toBeTrue();
                    });
                    it("should not add EMPTY on name errors", () -> {
                        expect(validator.contains("name", EMPTY)).toBeFalse();
                    });
                    it("should not add LOWER on name errors", () -> {
                        expect(validator.contains("name", LOWER)).toBeFalse();
                    });
                    it("should contain the key", () -> {
                        expect(validator.contains("name")).toBeTrue();
                    });
                    it("should add the \"name\" as a key in \"keys\" method", () -> {
                        expect(validator.keys().contains("name")).toBeTrue();
                    });
                });
                describe("when pass a object with empty values", () -> {
                    before(() -> {
                        SampleClass sample = new SampleClass();
                        sample.name = "";
                        validator = new SampleValidator(sample).validate();
                    });
                    it("should be invalid", () -> {
                        expect(validator.isValid()).toBeFalse();
                    });
                    it("should not add NULL name errors", () -> {
                        expect(validator.contains("name", NULL)).toBeFalse();
                    });
                    it("should add EMPTY on name errors", () -> {
                        expect(validator.contains("name", EMPTY)).toBeTrue();
                    });
                    it("should add LOWER on name errors", () -> {
                        expect(validator.contains("name", LOWER)).toBeTrue();
                    });
                });
                describe("when pass a object with feel characters", () -> {
                    before(() -> {
                        SampleClass sample = new SampleClass();
                        sample.name = "123";
                        validator = new SampleValidator(sample).validate();
                    });
                    it("should not add NULL name errors", () -> {
                        expect(validator.contains("name", NULL)).toBeFalse();
                    });
                    it("should add EMPTY on name errors", () -> {
                        expect(validator.contains("name", EMPTY)).toBeFalse();
                    });
                    it("should add LOWER on name errors", () -> {
                        expect(validator.contains("name", LOWER)).toBeTrue();
                    });
                });
            });

            describe("when validate a int field", () -> {
                before(() -> {
                    SampleClass sample = new SampleClass();
                    validator = new SampleValidator(sample).validate();
                });
                it("should not add NULL on strikeCount errors", () -> {
                    expect(validator.contains("strikeCount", NULL)).toBeFalse();
                });

                describe("when contains the wrong value", () -> {
                    before(() -> {
                        SampleClass sample = new SampleClass();
                        sample.strikeCount = 7;
                        validator = new SampleValidator(sample).validate();
                    });
                    it("should add GREATER on strikeCount errors", () -> {
                        expect(validator.contains("strikeCount", GREATER)).toBeTrue();
                    });
                });

            });

        });

    }

    private static class SampleClass {
        String name;
        int strikeCount;
    }

    private static class SampleValidator extends ValidatorBase<SampleClass> {
        public SampleValidator(SampleClass objectToValidate) {
            super(objectToValidate);
        }

        @Override
        protected void validate(SampleClass object) {
            addTo("name").when(object.name).isNull().isEmpty().length(i -> i.isLower(8));
            addTo("strikeCount").when(object.strikeCount).isNull().isGreater(6);
        }
    }
}