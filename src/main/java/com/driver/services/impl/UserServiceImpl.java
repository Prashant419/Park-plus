package com.driver.services.impl;

import com.driver.model.User;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository4;
    @Override
    public void deleteUser(Integer userId) {

        userRepository4.deleteById(userId);


    }

    @Override
    public User updatePassword(Integer userId, String password) {
        User updatedUser;

        updatedUser = userRepository4.findById(userId).get();

        updatedUser.setPassword(password);

        userRepository4.save(updatedUser);

        return updatedUser;

    }

    @Override
    public void register(String name, String phoneNumber, String password) {

        User user=new User();
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);

        userRepository4.save(user);


    }
}