package com.lostfound.demo.services;

import com.lostfound.demo.models.Item;
 
import java.util.ArrayList;
import java.util.List;
 
public class ItemSearcher {
 
    /**
     * Search items by keyword (searches name, description, location).
     * Uses Linear Search
     */
    public List<Item> searchByKeyword(List<Item> items, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>(items);
        }
 
        String query = keyword.toLowerCase().trim();
        List<Item> results = new ArrayList<>();
 
        // LINEAR SEARCH: iterate through every item
        for (Item item : items) {
            if (matchesKeyword(item, query)) {
                results.add(item);
            }
        }
 
        return results;
    }
 
    // Filter items by type ("lost" or "found").
    public List<Item> filterByType(List<Item> items, String type) {
        if (type == null || "all".equalsIgnoreCase(type)) {
            return new ArrayList<>(items);
        }
 
        List<Item> results = new ArrayList<>();
        for (Item item : items) {
            if (type.equalsIgnoreCase(item.getItemType())) {
                results.add(item);
            }
        }
        return results;
    }
 
    // Filter items by category (e.g. "Electronics", "Bags").
    public List<Item> filterByCategory(List<Item> items, String category) {
        if (category == null || category.trim().isEmpty()) {
            return new ArrayList<>(items);
        }
 
        List<Item> results = new ArrayList<>();
        for (Item item : items) {
            if (category.equalsIgnoreCase(item.getCategory())) {
                results.add(item);
            }
        }
        return results;
    }
 
    // Filter items by status ("active" or "returned").
    public List<Item> filterByStatus(List<Item> items, String status) {
        if (status == null || "all".equalsIgnoreCase(status)) {
            return new ArrayList<>(items);
        }
 
        List<Item> results = new ArrayList<>();
        for (Item item : items) {
            if (status.equalsIgnoreCase(item.getStatus())) {
                results.add(item);
            }
        }
        return results;
    }
 
    // Find a single item by ID using Linear Search O(n).
    public Item findById(List<Item> items, String id) {
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }
 
    // Private helper - Check if whether the item contains keyword in name, description, location.
    private boolean matchesKeyword(Item item, String keyword) {
        return item.getName().toLowerCase().contains(keyword)
                || item.getDescription().toLowerCase().contains(keyword)
                || item.getLocation().toLowerCase().contains(keyword);
    }
}