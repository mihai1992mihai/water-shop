package com.shop.watershop.services;

import com.shop.watershop.exception.EntityNotFoundException;
import com.shop.watershop.models.Item;
import com.shop.watershop.models.User;
import com.shop.watershop.repository.ItemRepository;
import com.shop.watershop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;


    @Autowired
    private UserRepository userRepository;

    public Item findById(Long id) {
        return unwrapItem(itemRepository.findById(id));
    }

    public Set<Item> findAll() {
        Set<Item> set = new HashSet();
        itemRepository.findAll().forEach(set::add);
        return set;
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public Set<Item> getUserItems(Long id) {
        return itemRepository.findByUsersId(id);

    }


    public void deleteByItemIdAndUserId(Long itemId, Long userId) {
        itemRepository.deleteByIdAndUsersId(userId, itemId);
    }

    public Item unwrapItem(Optional<Item> item) {
        if (item.isPresent()) {
            return item.get();
        } else {
            throw new EntityNotFoundException(item.get().getId());//???
        }
    }

    public Double totalPrice(List<Item> list) {
        Double amount = list.stream().mapToDouble(o -> o.getAmount() * o.getPrice()).sum();
        return amount;
    }

    public ArrayList<Item> newItemsList() {
        ArrayList<Item> list = new ArrayList<>();
        Item item1 =new Item();
        item1.setCategory(unwrapItem(itemRepository.findById(1L)).getCategory());
        item1.setPrice(unwrapItem(itemRepository.findById(1L)).getPrice());
        item1.setAmount(unwrapItem(itemRepository.findById(1L)).getAmount());
        Item item2 =new Item();
        item2.setCategory(unwrapItem(itemRepository.findById(2L)).getCategory());
        item2.setPrice(unwrapItem(itemRepository.findById(2L)).getPrice());
        item2.setAmount(unwrapItem(itemRepository.findById(2L)).getAmount());
        Item item3 =new Item();
        item3.setCategory(unwrapItem(itemRepository.findById(3L)).getCategory());
        item3.setPrice(unwrapItem(itemRepository.findById(3L)).getPrice());
        item3.setAmount(unwrapItem(itemRepository.findById(3L)).getAmount());
        list.add(item1);
        list.add(item2);
        list.add(item3);

        return list;
    }

    public ArrayList<Item> getUserItemsSortedByCategory(Long userId) {
        Set<Item> set = getUserItems(getLoggedUser().getId());
        ArrayList<Item> items = new ArrayList<>(set);

        ArrayList<Item> sortedItems = items.stream()
                .sorted(Comparator.comparing(Item::getCategory))
                .collect(Collectors.toCollection(ArrayList::new));

        return sortedItems;
    }

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;

        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String userEmail = ((UserDetails) principal).getUsername();
                user = userRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new UsernameNotFoundException("User with email " + userEmail + " not found."));


            } else {
                throw new UsernameNotFoundException("Invalid email or password.");
            }


        }
        return user;
    }

    @Override
    public Date setDate() {
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return date;
    }
}