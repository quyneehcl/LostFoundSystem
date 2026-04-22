package com.lostfound.demo.services;

import com.lostfound.demo.models.Item;
 
import java.util.ArrayList;
import java.util.List;
 
public class ItemSearcher {
 
    /**
     * Search items by keyword (searches name, description, location).
     * Uses LINEAR SEARCH - O(n) time complexity.
     * Nếu keyword rỗng → trả về toàn bộ danh sách.
     *
     * @param items   List of items to search
     * @param keyword Search keyword
     * @return Filtered list of matching items
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
 
    /**
     * Filter items by type ("lost" hoặc "found").
     * Nếu type là null hoặc "all" → trả về toàn bộ danh sách.
     */
    public List<Item> filterByType(List<Item> items, String type) {
        if (type == null || "all".equalsIgnoreCase(type)) {
            return new ArrayList<>(items);
        }
 
        List<Item> results = new ArrayList<>();
        for (Item item : items) {
            // Dùng getItemType() — đúng tên hàm trong abstract class Item
            if (type.equalsIgnoreCase(item.getItemType())) {
                results.add(item);
            }
        }
        return results;
    }
 
    /**
     * Filter items by category (e.g. "Electronics", "Bags").
     * Nếu category rỗng → trả về toàn bộ danh sách.
     */
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
 
    /**
     * Filter items by status ("active" hoặc "returned").
     * Nếu status là null hoặc "all" → trả về toàn bộ danh sách.
     */
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
 
    /**
     * Find a single item by ID using Linear Search O(n).
     * Trả về null nếu không tìm thấy.
     */
    public Item findById(List<Item> items, String id) {
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }
 
    /**
     * Private helper — kiểm tra item có chứa keyword trong
     * name, description, hoặc location không.
     */
    private boolean matchesKeyword(Item item, String keyword) {
        return item.getName().toLowerCase().contains(keyword)
                || item.getDescription().toLowerCase().contains(keyword)
                || item.getLocation().toLowerCase().contains(keyword);
    }
}