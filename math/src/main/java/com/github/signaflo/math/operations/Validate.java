package com.github.signaflo.math.operations;

import lombok.NonNull;

/**
 * Validation methods.
 *
 * @author Jacob Rachiele
 * Jul. 29, 2017
 */
public final class Validate {

    private Validate() {}

    public static <T> void argumentsNotNull(@NonNull Class<T> clazz, Object... args) {
        if (args == null) {
            throw new NullPointerException("null-valued " + clazz.getSimpleName() + " given as " +
                                           "an argument.");
        }
        for (Object arg : args) {
            if (arg == null) {
                throw new NullPointerException("null-valued " + clazz.getSimpleName() + " given as " +
                                               "an argument.");
            }
        }
    }

}
