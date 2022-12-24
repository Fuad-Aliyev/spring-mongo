package com.tutorial.service;

import com.tutorial.model.User;

import java.util.List;

public interface UserService {
    List<User> find();
    void aggregate();
}
