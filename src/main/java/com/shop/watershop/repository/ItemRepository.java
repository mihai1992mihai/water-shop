package com.shop.watershop.repository;

import com.shop.watershop.models.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    Optional<Item> deleteByIdAndUsersId(Long userId, Long itemId);

    Set<Item> findByUsersId(Long id);

}
