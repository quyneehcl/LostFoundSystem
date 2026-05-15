package com.lostfound.demo.controllers;

import com.lostfound.demo.models.FoundItem;
import com.lostfound.demo.models.Item;
import com.lostfound.demo.models.LostItem;
import com.lostfound.demo.models.MatchResult;
import com.lostfound.demo.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Handles REST API requests from the frontend for item management.
 * Provides endpoints for creating, reading, updating, deleting items, searching and matching functionality.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ItemController {

    @Autowired
    private ItemService itemService;

    // POST /api/items 
    @PostMapping("/items")
    public ResponseEntity<Map<String, Object>> createItem(@RequestBody Map<String, String> body) {
        try {
            String type        = body.get("type");
            String name        = body.get("name");
            String category    = body.get("category");
            String description = body.get("description");
            String location    = body.get("location");
            String contactInfo = body.get("contactInfo");
            String imageUrl    = body.get("imageUrl");
            String reportedBy  = body.get("reportedBy");

            LocalDate date;
            if (body.get("date") != null && !body.get("date").isEmpty()) {
                date = LocalDate.parse(body.get("date"));
            } else {
                date = LocalDate.now();
            }

            String id = UUID.randomUUID().toString();
            Item item;
            if ("found".equalsIgnoreCase(type)) {
                item = new FoundItem(id, name, category, description,
                        location, date, contactInfo, imageUrl, reportedBy);
            } else {
                item = new LostItem(id, name, category, description,
                        location, date, contactInfo, imageUrl, reportedBy);
            }

            itemService.addItem(item);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Item reported successfully");
            response.put("item", item.toMap());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // GET /api/items 
    @GetMapping("/items")
    public ResponseEntity<Map<String, Object>> getAllItems() {
        List<Item> items = itemService.getAllItems();

        List<Map<String, Object>> itemsMap = new ArrayList<>();
        for (Item item : items) {
            itemsMap.add(item.toMap());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("count", items.size());
        response.put("items", itemsMap);
        return ResponseEntity.ok(response);
    }

    // GET /api/items/search?keyword= 
    @GetMapping("/items/search")
    public ResponseEntity<Map<String, Object>> searchItems(@RequestParam String keyword) {
        List<Item> items = itemService.searchItems(keyword);

        List<Map<String, Object>> itemsMap = new ArrayList<>();
        for (Item item : items) {
            itemsMap.add(item.toMap());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("count", items.size());
        response.put("items", itemsMap);
        return ResponseEntity.ok(response);
    }

    // GET /api/items/matches/{id}
    @GetMapping("/items/matches/{id}")
    public ResponseEntity<Map<String, Object>> findMatches(@PathVariable String id) {
        List<MatchResult> matches = itemService.findMatchesById(id);

        List<Map<String, Object>> matchesMap = new ArrayList<>();
        for (MatchResult match : matches) {
            matchesMap.add(match.toMap());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("count", matches.size());
        response.put("matches", matchesMap);
        return ResponseEntity.ok(response);
    }

    // PUT /api/items/return/{id}
    @PutMapping("/items/return/{id}")
    public ResponseEntity<Map<String, Object>> markAsReturned(@PathVariable String id) {
        boolean success = itemService.markAsReturned(id);

        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("success", true);
            response.put("message", "Item marked as returned");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Item not found");
            return ResponseEntity.badRequest().body(response);
        }
    }

    // DELETE /api/items/{id}
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Map<String, Object>> deleteItem(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            Item item    = itemService.getItemById(id);

            // Item not found
            if (item == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Item not found");
                return ResponseEntity.badRequest().body(response);
            }

            // Check admission
            boolean isAdmin    = "admin@vinuni.edu.vn".equals(email);
            boolean isReporter = email != null && email.equals(item.getReportedBy());

            if (!isAdmin && !isReporter) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Forbidden");
                return ResponseEntity.status(403).body(response);
            }

            boolean success = itemService.deleteItem(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", success);
            response.put("message", success ? "Item deleted successfully" : "Delete failed");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}