package com.tutorial.service.impl;

import com.tutorial.model.GroceryItem;
import com.tutorial.repository.ItemRepository;
import com.tutorial.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public void createGroceryItems() {
        System.out.println("Data creation started...");
        itemRepository.save(new GroceryItem("Whole Wheat Biscuit", "Whole Wheat Biscuit", 5, "snacks"));
        itemRepository.save(new GroceryItem("Kodo Millet", "XYZ Kodo Millet healthy", 2, "millets"));
        itemRepository.save(new GroceryItem("Dried Red Chilli", "Dried Whole Red Chilli", 2, "spices"));
        itemRepository.save(new GroceryItem("Pearl Millet", "Healthy Pearl Millet", 1, "millets"));
        itemRepository.save(new GroceryItem("Cheese Crackers", "Bonny Cheese Crackers Plain", 6, "snacks"));
        System.out.println("Data creation complete...");
    }

    @Override
    public void showAllGroceryItems() {
        itemRepository.findAll().forEach(item -> System.out.println(getItemDetails(item)));
    }

    @Override
    public void getGroceryItemByName(String name) {
        System.out.println("Getting item by name: " + name);
        GroceryItem item = itemRepository.findItemByName(name);
        getItemDetails(item);
    }

    @Override
    public void getItemsByCategory(String category) {
        System.out.println("Getting items for the category " + category);
        List<GroceryItem> list = itemRepository.findAll(category);
        list.forEach(item -> System.out.println("Name: " + item.getName() + ", Quantity: " + item.getQuantity()));
    }

    @Override
    public void findCountOfGroceryItems() {
        long count = itemRepository.count();
        System.out.println("Number of documents in the collection: " + count);
    }

    @Override
    public void updateCategoryName(String category) {
        // Change to this new value
        String newCategory = "munchies";

        // Find all the items with the category snacks
        List<GroceryItem> list = itemRepository.findAll(category);

        list.forEach(item -> {
            // Update the category in each document
            item.setCategory(newCategory);
        });

        // Save all the items in database
        List<GroceryItem> itemsUpdated = itemRepository.saveAll(list);
        if (itemsUpdated != null)
            System.out.println("Successfully updated " + itemsUpdated.size() + " items.");
    }

    @Override
    public void deleteGroceryItem(String id) {
        itemRepository.deleteById(id);
        System.out.println("Item with id " + id + " deleted...");
    }

    private String getItemDetails(GroceryItem item) {
        System.out.println(
                "Item Name: " + item.getName() +
                        ", \nQuantity: " + item.getQuantity() +
                        ", \nItem Category: " + item.getCategory()
        );
        return "";
    }
}
