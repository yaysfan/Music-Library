package com.yayfan.music.domain.auth;

public class UsernameTakenException extends RuntimeException {
    public UsernameTakenException() {
        super("Username already taken.");
    }
}
