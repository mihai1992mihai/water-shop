package com.shop.watershop.web;

import com.shop.watershop.models.User;
import java.util.Optional;
import java.util.Set;


public interface UserService {

    public User save(User user);

    User findByEmail(String email);

    User findById(Long id);

    Set<User> findAll();

    public User unwrapUser(Optional<User> user);

    public User getLoggedUser();



}
