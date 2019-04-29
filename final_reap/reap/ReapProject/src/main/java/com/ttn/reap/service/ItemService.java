package com.ttn.reap.service;

import com.ttn.reap.entity.Item;
import com.ttn.reap.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

    public List<Item> findAll()
    {
        return itemRepository.findAll();
    }
    public Item findById(Long id){
        return itemRepository.findById(id);
    }
}
