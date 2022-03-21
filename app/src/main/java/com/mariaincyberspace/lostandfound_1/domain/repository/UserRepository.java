package com.mariaincyberspace.lostandfound_1.domain.repository;

import com.mariaincyberspace.lostandfound_1.domain.model.User;

public interface UserRepository {
    void addUser(User user);
    void deleteUser(User user);
}
