package com.lostfound.demo.models;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * LostItem - Data class representing a lost item.
 * @author Ngu Hoang Nguyen
 */

public class LostItem extends Item {
    public LostItem(String id, String name, String category, String description, String location, LocalDate date, String contactInfo, String imageUrl, String reportedBy) {
        // Initialization
        super(id, name, category, description, location, date, contactInfo);
        this.setImageUrl(imageUrl);
        this.setReportedBy(reportedBy);
    }
    // Implementation of abstract methods getItemType, getSummary
    @Override
    public String getItemType() {
        return "lost";
    }
    @Override
    public String getSummary() {
        return String.format("[LOST] %s | Category: %s | Location: %s | Date: %s", getName(), getCategory(), getLocation(), getDate());
    }
    @Override
    public HashMap<String, Object> toMap() {
        // Convert LostItem to a Map 
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("name", getName());
        map.put("category", getCategory());
        map.put("description", getDescription());
        map.put("location", getLocation());
        map.put("date", getDate().toString());
        map.put("contactInfo", getContactInfo());
        map.put("imageUrl", getImageUrl());
        map.put("reportedBy", getReportedBy());

        map.put("type", getItemType());
        map.put("status", getStatus());
        return map;
    }


}
