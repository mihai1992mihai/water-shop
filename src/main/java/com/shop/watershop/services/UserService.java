package com.shop.watershop.services;

import com.shop.watershop.models.Item;
import com.shop.watershop.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;


public interface UserService {

    public User save(User user);
    User findByEmail(String email);

    User findById(Long id);

    Set<User> findAll();

    public User unwrapUser(Optional<User> user);
    public User getLoggedUser();


    public ArrayList<Item> newOrUpdateList();


}
