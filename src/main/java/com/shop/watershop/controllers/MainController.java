package com.shop.watershop.controllers;

import com.shop.watershop.models.Item;
import com.shop.watershop.models.ItemsList;
import com.shop.watershop.models.User;
import com.shop.watershop.repository.UserRepository;
import com.shop.watershop.services.ItemService;
import com.shop.watershop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Controller

public class MainController {

    @Autowired
    private ItemService itemService;
    @Autowired

    private UserService userService;


    @GetMapping("/home")
    public String home(Model model) {

        ItemsList itemsList = new ItemsList(userService.newOrUpdateList());
        itemsList.setDate((itemService.setDate()));

        model.addAttribute("itemsList", itemsList);



        return "index";
    }


    @PostMapping("/post")
    public String handleSubmit(@Valid @ModelAttribute("itemsList") ItemsList itemsList, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "index";
        }

        Set<Item> set = new HashSet<>(itemsList.getItems());
        set.forEach(item -> item.setDate(itemsList.getDate()));

        User user = userService.getLoggedUser();
        user.setItems(set);
        userService.save(user);



        return "redirect:/inventory";

    }

    @GetMapping("/inventory")
    public String getInventory( Model model ) {

        ArrayList<Item> items = itemService.getUserItemsSortedByCategory(userService.getLoggedUser().getId());
        Double total = itemService.totalPrice(items);

        model.addAttribute("items", items);
        model.addAttribute("total", total);
        return "inventory";
    }


}
