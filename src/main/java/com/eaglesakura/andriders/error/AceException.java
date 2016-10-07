package com.eaglesakura.andriders.error;

/**
 * ACE関係で処理で問題が発生した
 */
public class AceException extends Exception {
    public AceException() {
    }

    public AceException(String message) {
        super(message);
    }

    public AceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AceException(Throwable cause) {
        super(cause);
    }

}
