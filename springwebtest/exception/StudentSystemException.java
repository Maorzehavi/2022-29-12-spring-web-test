package com.maorzehavi.springwebtest.exception;

import java.security.PrivilegedActionException;

public class StudentSystemException extends RuntimeException {

    public StudentSystemException(String message) {
        super(message);
    }

    public StudentSystemException(String message, Throwable cause) {
        super(message, cause);
    }



}
