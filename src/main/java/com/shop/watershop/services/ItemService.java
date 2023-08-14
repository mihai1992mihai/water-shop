package com.shop.watershop.services;

import com.shop.watershop.models.Item;
import com.shop.watershop.models.User;

import java.util.*;

public interface ItemService {

    public Item findById(Long id);
    public Set<Item> findAll();
    public Item saveItem(Item item);
    public void deleteByItemIdAndUserId(Long itemId, Long userId);
    public Item unwrapItem(Optional<Item> item);
    public Double totalPrice(List<Item> list);
    public ArrayList<Item> newItemsList();

    public ArrayList<Item> getUserItemsSortedByCategory(Long userId);
    public User getLoggedUser();
    public Set<Item> getUserItems(Long userId);
    public Date setDate();


}
