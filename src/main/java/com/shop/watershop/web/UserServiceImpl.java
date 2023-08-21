package com.shop.watershop.web;

import com.shop.watershop.models.Role;
import com.shop.watershop.models.User;
import com.shop.watershop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Lazy
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemService itemService;

    public User save(User user) {
        if (user.getId() != null) {

            return userRepository.save(user);
        } else {
            Role role = new Role("ROLE_USER");

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);
            user.setRoles(roleSet);
        }

        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {

        return unwrapUser(userRepository.findByEmail(email));
    }

    @Override
    public User findById(Long id) {
        return unwrapUser(userRepository.findById(id));
    }


    public Set<User> findAll() {
        Set<User> set = new HashSet();
        userRepository.findAll().forEach(set::add);
        return set;
    }

    public User unwrapUser(Optional<User> user) {
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("Invalid email");
        }
    }

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;

        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String userEmail = ((UserDetails) principal).getUsername();
                user = findByEmail(userEmail);
            }
        } else {
            throw new UsernameNotFoundException("Invalid email or password.");
        }


        return user;
    }

}
