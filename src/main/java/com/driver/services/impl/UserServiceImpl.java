package com.driver.services.impl;

import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository4;
    @Override
    public void deleteUser(Integer userId) {

    }

    @Override
    public User updatePassword(Integer userId, String password) {

    }

    @Override
    public void register(String name, String phoneNumber, String password) {

    }
}
