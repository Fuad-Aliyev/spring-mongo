package com.tutorial;

import com.tutorial.model.GroceryItem;
import com.tutorial.repository.CustomItemRepository;
import com.tutorial.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
//@EnableMongoRepositories
public class SpringMongoApplication implements CommandLineRunner {
	@Autowired
	private ItemRepository groceryItemRepo;
//	@Autowired
//	private CustomItemRepository customRepo;

	public static void main(String[] args) {
		SpringApplication.run(SpringMongoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("-------------CREATE GROCERY ITEMS-------------------------------\n");

		createGroceryItems();

		System.out.println("\n----------------SHOW ALL GROCERY ITEMS---------------------------\n");

		showAllGroceryItems();

		System.out.println("\n--------------GET ITEM BY NAME-----------------------------------\n");

		getGroceryItemByName("Whole Wheat Biscuit");

		System.out.println("\n-----------GET ITEMS BY CATEGORY---------------------------------\n");

		getItemsByCategory("millets");

		System.out.println("\n-----------UPDATE CATEGORY NAME OF SNACKS CATEGORY----------------\n");

		updateCategoryName("snacks");

		System.out.println("\n-----------UPDATE QUANTITY OF A GROCERY ITEM------------------------\n");

		updateItemQuantity("Bonny Cheese Crackers Plain", 10);

		System.out.println("\n----------DELETE A GROCERY ITEM----------------------------------\n");

		deleteGroceryItem("Kodo Millet");

		System.out.println("\n------------FINAL COUNT OF GROCERY ITEMS-------------------------\n");

		findCountOfGroceryItems();

		System.out.println("\n-------------------THANK YOU---------------------------");
	}

	private void createGroceryItems() {
		System.out.println("Data creation started...");
		groceryItemRepo.save(new GroceryItem("Whole Wheat Biscuit", "Whole Wheat Biscuit", 5, "snacks"));
		groceryItemRepo.save(new GroceryItem("Kodo Millet", "XYZ Kodo Millet healthy", 2, "millets"));
		groceryItemRepo.save(new GroceryItem("Dried Red Chilli", "Dried Whole Red Chilli", 2, "spices"));
		groceryItemRepo.save(new GroceryItem("Pearl Millet", "Healthy Pearl Millet", 1, "millets"));
		groceryItemRepo.save(new GroceryItem("Cheese Crackers", "Bonny Cheese Crackers Plain", 6, "snacks"));
		System.out.println("Data creation complete...");
	}

	// 1. Show all the data
	private void showAllGroceryItems() {
		groceryItemRepo.findAll().forEach(item -> System.out.println(getItemDetails(item)));
	}

	// 2. Get item by name
	private void getGroceryItemByName(String name) {
		System.out.println("Getting item by name: " + name);
		GroceryItem item = groceryItemRepo.findItemByName(name);
		getItemDetails(item);
	}

	// 3. Get name and quantity of a all items of a particular category
	private void getItemsByCategory(String category) {
		System.out.println("Getting items for the category " + category);
		List<GroceryItem> list = groceryItemRepo.findAll(category);
		list.forEach(item -> System.out.println("Name: " + item.getName() + ", Quantity: " + item.getQuantity()));
	}

	// 4. Get count of documents in the collection
	private void findCountOfGroceryItems() {
		long count = groceryItemRepo.count();
		System.out.println("Number of documents in the collection: " + count);
	}

	private String getItemDetails(GroceryItem item) {
		System.out.println(
				"Item Name: " + item.getName() +
						", \nQuantity: " + item.getQuantity() +
						", \nItem Category: " + item.getCategory()
		);
		return "";
	}

	private void updateCategoryName(String category) {
		// Change to this new value
		String newCategory = "munchies";

		// Find all the items with the category snacks
		List<GroceryItem> list = groceryItemRepo.findAll(category);

		list.forEach(item -> {
			// Update the category in each document
			item.setCategory(newCategory);
		});

		// Save all the items in database
		List<GroceryItem> itemsUpdated = groceryItemRepo.saveAll(list);
		if (itemsUpdated != null)
			System.out.println("Successfully updated " + itemsUpdated.size() + " items.");
	}

	private void deleteGroceryItem(String id) {
		groceryItemRepo.deleteById(id);
		System.out.println("Item with id " + id + " deleted...");
	}

	// UPDATE
	public void updateItemQuantity(String name, float newQuantity) {
		System.out.println("Updating quantity for " + name);
//		customRepo.updateItemQuantity(name, newQuantity);
	}
}
