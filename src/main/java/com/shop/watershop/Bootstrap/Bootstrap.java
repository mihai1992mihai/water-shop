package com.shop.watershop.Bootstrap;

import com.shop.watershop.models.Item;
import com.shop.watershop.models.ItemsList;
import com.shop.watershop.models.User;
import com.shop.watershop.repository.ItemRepository;
import com.shop.watershop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void run(String... args) throws Exception {
        Item item1 = new Item();
        item1.setCategory("Bottle 5L");
        item1.setPrice(20.0);
        item1.setAmount(0.0);
        itemRepository.save(item1);

        Item item2 = new Item();
        item2.setCategory("Bottle 2L");
        item2.setPrice(10.0);
        item2.setAmount(0.0);
        itemRepository.save(item2);

        Item item3 = new Item();
        item3.setCategory("Bottle 1L");
        item3.setPrice(5.0);
        item3.setAmount(0.0);
        itemRepository.save(item3);

        User user = new User();
        user.setEmail("mihai@yahoo.com");
        user.setPassword(bCryptPasswordEncoder.encode("123456"));
        user.setFirstName("Mihai");
        user.setLastName("Juncu");
        userRepository.save(user);



    }
}
