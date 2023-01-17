package com.anand.menu.item;

import com.anand.menu.item.Item;
import com.anand.menu.item.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/menus/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Item>> findAll(){
        List<Item> items = itemService.findAll();
        return ResponseEntity.ok().body(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> find(@PathVariable("id") Long id){
        Optional<Item> item = itemService.find(id);
        return ResponseEntity.of(item);
    }

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody Item item){
        Item created = itemService.create(item);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> update(
            @PathVariable("id") Long id,
            @RequestBody Item updatedItem) {

        Optional<Item> updated = itemService.update(id, updatedItem);

        return updated
                .map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> {
                    Item created = itemService.create(updatedItem);
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(created.getId())
                            .toUri();
                    return ResponseEntity.created(location).body(created);
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Item> delete(@PathVariable("id") Long id){
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
