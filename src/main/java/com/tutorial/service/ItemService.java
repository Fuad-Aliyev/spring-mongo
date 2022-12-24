package com.tutorial.service;

public interface ItemService {
    void createGroceryItems();

    void showAllGroceryItems();

    void getGroceryItemByName(String name);

    void getItemsByCategory(String category);

    void findCountOfGroceryItems();

    void updateCategoryName(String category);

    void deleteGroceryItem(String id);
}
