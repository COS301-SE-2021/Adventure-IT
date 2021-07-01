package com.adventureit.userservice.Exceptions;

public class UserExceptions extends RuntimeException {

    /**
     * Constructs a new user exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public UserExceptions() {
    }

    /**
     * Constructs a new exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public UserExceptions(String message) {
        super(message);
    }


    /**
     * Constructs a new user exception with the specified detail message and
     * cause. Note that the detail message associated with
     * {@code cause} is not automatically incorporated in
     * this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A {@code null} value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     */
    public UserExceptions(String message, Throwable cause) {
        super(message, cause);
    }

}
