package com.shop.watershop.services;

import com.shop.watershop.models.Role;
import com.shop.watershop.models.User;
import com.shop.watershop.repository.UserRepository;
import com.shop.watershop.web.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {


    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;



    @Test
    void save_whenInvoked_returnsSavedUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword("password");
        Role role = new Role("ROLE_USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRoles(roleSet);

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.save(user);

        assertEquals(user, result);
    }

    @Test
    void findByEmail_whenInvoked_returnsUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword("password");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        User result = userService.findByEmail("user@example.com");

        assertEquals(user, result);
    }

    @Test
    void findById_whenInvoked_returnsUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword("password");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findById(1L);

        assertEquals(user, result);
    }


    @Test
    void findAll_whenInvoked_returnsSetOfUsers() {

        Set<User> set = new HashSet<>();
        set.add(new User());
        set.add(new User());
        set.add(new User());

        when(userRepository.findAll()).thenReturn(set);

        Set<User> result = userService.findAll();

        assertEquals(set, result);
    }

    @Test
    void unwrapUser_ifUserIsPresent_returnsUser() {
        Optional<User> user = Optional.of(new User());

        when(userRepository.findById(1L)).thenReturn(user);

        User result = userService.unwrapUser(user);

        assertEquals(user.get(), result);
    }

    @Test
    void getLoggedUser_whenInvoked_returnsUser() {

        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);

        UserDetails userDetails = mock(UserDetails.class);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("user@example.com");


        User result = userService.getLoggedUser();


        assertEquals(user, result);
    }


}