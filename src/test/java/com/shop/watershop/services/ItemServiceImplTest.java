package com.shop.watershop.services;

import com.shop.watershop.models.Item;
import com.shop.watershop.models.User;
import com.shop.watershop.repository.ItemRepository;
import com.shop.watershop.repository.UserRepository;
import com.shop.watershop.web.ItemService;
import com.shop.watershop.web.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ItemServiceImplTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Test
    public void findById() {

        Item item = new Item();
        item.setId(1L);
        item.setCategory("Books");
        item.setPrice(10.0);
        item.setAmount(2.0);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Item result = itemService.findById(1L);

        assertEquals(item, result);
    }

    @Test
    void findAll() {
        Item item1 = new Item();
        item1.setId(1L);
        item1.setCategory("Books");
        item1.setPrice(10.0);
        item1.setAmount(2.0);
        Item item2 = new Item();
        item2.setId(2L);
        item2.setCategory("Games");
        item2.setPrice(20.0);
        item2.setAmount(3.0);

        Set<Item> expected = Set.of(item1, item2);
        when(itemRepository.findAll()).thenReturn(expected);

        Set<Item> result = itemService.findAll();

        assertEquals(expected, result);
    }

    @Test
    void saveItem() {
        Item item = new Item();
        item.setId(1L);
        item.setCategory("Books");
        item.setPrice(10.0);
        item.setAmount(2.0);
        when(itemRepository.save(item)).thenReturn(item);

        Item result = itemService.saveItem(item);

        assertEquals(item, result);
    }

    @Test
    void getUserItems() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword("password");
        Item item1 = new Item();
        item1.setId(1L);
        item1.setCategory("Books");
        item1.setPrice(10.0);
        item1.setAmount(2.0);
        Item item2 = new Item();
        item2.setId(2L);
        item2.setCategory("Games");
        item2.setPrice(20.0);
        item2.setAmount(3.0);
        Set<Item> expected = Set.of(item1, item2);
        user.setItems(expected);

        when(itemRepository.findByUsersId(1L)).thenReturn(expected);

        Set<Item> result = itemService.getUserItems(1L);

        assertEquals(expected, result);
    }

    @Test
    void deleteByItemIdAndUserId() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword("password");
        Item item1 = new Item();
        item1.setId(1L);
        item1.setCategory("Books");
        item1.setPrice(10.0);
        item1.setAmount(2.0);

        Set<Item> items = Set.of(item1);
        user.setItems(items);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));


        itemService.deleteByItemIdAndUserId(1L, 1L);


        verify(itemRepository).deleteByIdAndUsersId(1L, 1L);
    }

    @Test
    void unwrapItem() {
        Item item = new Item();
        item.setId(1L);

        Optional<Item> optionalItem = Optional.of(item);

        Item result = itemService.unwrapItem(optionalItem);

        assertEquals(item, result);
    }

    @Test
    void totalPrice() {
        Item item1 = new Item();
        item1.setPrice(10.0);
        item1.setAmount(2.0);
        Item item2 = new Item();
        item2.setPrice(20.0);
        item2.setAmount(3.0);
        List<Item> list = Arrays.asList(item1, item2);
        Double expected = 80.0;

        Double result = itemService.totalPrice(list);

        assertEquals(expected, result);
    }



    @Test
    void getUserItemsSortedByCategory() {
        User user = new User();
        user.setId(1L);

        Item item1 = new Item();
        item1.setCategory("Books");
        item1.setId(1L);

        Item item2 = new Item();
        item2.setCategory("Games");
        item2.setId(2L);

        Set<Item> items = Set.of(item1, item2);
        ArrayList<Item> list = new ArrayList<>(items);


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
        when(itemService.getUserItems(1L)).thenReturn(items);


        ArrayList<Item> sortedItems = itemService.getUserItemsSortedByCategory(1L);

        assertThat(sortedItems.get(0).getCategory()).isEqualTo("Books");
        assertThat(sortedItems.get(1).getCategory()).isEqualTo("Games");
    }

    @Test
    void getLoggedUser() {
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

        User result = itemService.getLoggedUser();

        assertEquals(user, result);
    }

    @Test
    void setDateFromExistingItem() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword("password");
        Item item = new Item();
        item.setId(1L);
        item.setCategory("Books");
        item.setPrice(10.0);
        item.setAmount(2.0);

        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        item.setDate(date);
        Set<Item> items = new HashSet<>(Collections.singleton(item));
        user.setItems(items);

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("user@example.com");

        // when
        Date result = itemService.setDate();

        // then
        assertEquals(date, result);
    }

    @Test
    void setDateNew() {

        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        User user = mock(User.class);
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword("password");
        Set<Item> itemsMock = mock(Set.class);

        when(user.getItems()).thenReturn(itemsMock);
        when(itemsMock.isEmpty()).thenReturn(true);
        when(userService.getLoggedUser()).thenReturn(user);
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("user@example.com");


        Date result = itemService.setDate();

        assertEquals(date, result);
    }
}