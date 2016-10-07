package com.eaglesakura.andriders.error;

/**
 * セッション制御失敗時の例外
 */

public class SessionControlException extends AceException {
    public SessionControlException() {
    }

    public SessionControlException(String message) {
        super(message);
    }

    public SessionControlException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionControlException(Throwable cause) {
        super(cause);
    }

}
