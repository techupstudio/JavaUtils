package com.techupstudio.otc_chingy.mychurch.core.utils.general.testing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;

/**
 * Contains common assertions.
 */
public final class Preconditions {

    private Preconditions() {
        // Utility class.
    }

    public static void checkArgument(boolean expression, @NonNull String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    @NonNull
    public static <T> T checkNonNull(@Nullable T arg) {
        return checkNonNull(arg, "Argument must not be null");
    }

    @NonNull
    public static <T> T checkNonNull(@Nullable T arg, @NonNull String message) {
        if (arg == null) {
            throw new NullPointerException(message);
        }
        return arg;
    }

    @NonNull
    public static String checkNotEmpty(@Nullable String string) {
        if (string == null || string.length() == 0) {
            throw new IllegalArgumentException("Must not be null or empty");
        }
        return string;
    }

    @NonNull
    public static <T extends Collection<Y>, Y> T checkNotEmpty(@NonNull T objects) {
        if (objects.isEmpty()) {
            throw new IllegalArgumentException("Must not be empty.");
        }
        return objects;
    }

    @NonNull
    public static <T> T[] checkNotEmpty(@NonNull T[] objects) {
        if (objects.length == 0) {
            throw new IllegalArgumentException("Must not be empty.");
        }
        return objects;
    }

    /**
     * Validates that the value is true
     *
     * @param val object to test
     */
    public static void checkTrue(boolean val) {
        if (!val)
            throw new IllegalArgumentException("Must be true");
    }

    /**
     * Validates that the value is true
     *
     * @param val object to test
     * @param msg message to output if validation fails
     */
    public static void checkTrue(boolean val, String msg) {
        if (!val)
            throw new IllegalArgumentException(msg);
    }

    /**
     * Validates that the value is false
     *
     * @param val object to test
     */
    public static void checkFalse(boolean val) {
        if (val)
            throw new IllegalArgumentException("Must be false");
    }

    /**
     * Validates that the value is false
     *
     * @param val object to test
     * @param msg message to output if validation fails
     */
    public static void checkFalse(boolean val, String msg) {
        if (val)
            throw new IllegalArgumentException(msg);
    }

    /**
     * Validates that the array contains no null elements
     *
     * @param objects the array to test
     */
    public static <T> T[] checkNoNullElements(@NonNull T[] objects) {
        return checkNoNullElements(objects, "Array must not contain any null objects");
    }

    /**
     * Validates that the array contains no null elements
     *
     * @param objects the array to test
     * @param msg     message to output if validation fails
     */
    public static <T> T[] checkNoNullElements(@NonNull T[] objects, String msg) {
        for (Object obj : objects)
            if (obj == null)
                throw new IllegalArgumentException(msg);
        return objects;
    }


    /**
     * Validates that the array contains no null elements
     *
     * @param collection the list to test
     */
    public static <T extends Collection<?>> T checkNoNullElements(@NonNull T collection) {
        return checkNoNullElements(collection, "Array must not contain any null objects");
    }

    /**
     * Validates that the array contains no null elements
     *
     * @param collection the list to test
     * @param msg        message to output if validation fails
     */
    public static <T extends Collection<?>> T checkNoNullElements(@NonNull T collection, String msg) {
        for (Object obj : collection)
            if (obj == null)
                throw new IllegalArgumentException(msg);
        return collection;
    }


}

