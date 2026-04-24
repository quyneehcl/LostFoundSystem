package com.lostfound.demo.models;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Item = Abstract base class for all reportable objects in the Lost & Found system.
 * @author Nguyen The Minh Duc
 */

public abstract class Item {
    private String id;
    private String name;
    private String category;
    private String description;
    private String location;
    private LocalDate date;
    private String contactInfo;
    private String status;
    private String imageUrl;
    private String reportedBy;

    public Item(String id, String name, String category, String description, String location, LocalDate date, String contactInfo, String imageUrl, String reportedBy){
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.location = location;
        this.date = date;
        this.contactInfo = contactInfo;
        this.imageUrl = imageUrl;
        this.reportedBy = reportedBy;
        this.status = "active";
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    // Abstract Methods
    public abstract String getItemType();

    public abstract String getSummary();

    public abstract HashMap<String, Object> toMap();
}
