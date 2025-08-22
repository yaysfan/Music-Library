package com.yayfan.music.domain.user;

import java.util.Optional;

public interface UserStorage {
    Optional<User> findByUsername(String name);

    void save(User user);
}
