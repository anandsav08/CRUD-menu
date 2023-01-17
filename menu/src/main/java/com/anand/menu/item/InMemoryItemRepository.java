package com.anand.menu.item;

import com.anand.menu.item.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InMemoryItemRepository extends CrudRepository<Item,Long> {
}
