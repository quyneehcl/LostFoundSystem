package com.lostfound.demo.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.lostfound.demo.models.FoundItem;
import com.lostfound.demo.models.Item;
import com.lostfound.demo.models.LostItem;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Handles all Firestore database operations for items
 * The data access layer between ItemService and Firebase Firestore.
 * @author Nguyen Minh Quyen
 */
@Repository
public class ItemRepository {

    private static final String COLLECTION = "items";

    private Firestore getDb() {
        return FirestoreClient.getFirestore();
    }

    /**
     * Add a new item to Firestore.
     */
    public void addItem(Item item) {
        try {
            getDb().collection(COLLECTION)
                   .document(item.getId())
                   .set(item.toMap());
        } catch (Exception e) {
            System.err.println("Error adding item: " + e.getMessage());
        }
    }


    // Get all items from Firestore
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> future = getDb().collection(COLLECTION).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                Item item = documentToItem(doc);
                if (item != null) {
                    items.add(item);
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting items: " + e.getMessage());
        }
        return items;
    }

    // Update the status of an item by ID
    public boolean updateItemStatus(String id, String status) {
        try {
            getDb().collection(COLLECTION)
                   .document(id)
                   .update("status", status);
            return true;
        } catch (Exception e) {
            System.err.println("Error updating item status: " + e.getMessage());
            return false;
        }
    }


    // Delete an item by ID from Firestore
    public boolean deleteItem(String id) {
        try {
            getDb().collection(COLLECTION)
                   .document(id)
                   .delete();
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting item: " + e.getMessage());
            return false;
        }
    }


    // Convert a Firestore document to a LostItem or FoundItem object
    private Item documentToItem(QueryDocumentSnapshot doc) {
        try {
            Map<String, Object> data = doc.getData();

            String type        = (String) data.get("type");
            String id          = doc.getId();
            String name        = (String) data.get("name");
            String category    = (String) data.get("category");
            String description = (String) data.get("description");
            String location    = (String) data.get("location");
            String contactInfo = (String) data.get("contactInfo");
            String imageUrl    = (String) data.get("imageUrl");
            String reportedBy  = (String) data.get("reportedBy");
            String status      = (String) data.get("status");

            String dateStr = (String) data.get("date");
            LocalDate date = (dateStr != null && !dateStr.isEmpty())
                ? LocalDate.parse(dateStr) : LocalDate.now();

            Item item;
            if ("lost".equals(type)) {
                item = new LostItem(id, name, category, description,
                        location, date, contactInfo, imageUrl, reportedBy);
            } else {
                item = new FoundItem(id, name, category, description,
                        location, date, contactInfo, imageUrl, reportedBy);
            }

            if (status != null) {
                item.setStatus(status);
            }

            return item;

        } catch (Exception e) {
            System.err.println("Error converting document: " + e.getMessage());
            return null;
        }
    }
}