package com.lostfound.demo.models;

import java.util.HashMap;
import java.util.Map;

public class MatchResult implements Comparable<MatchResult> {
    private String itemId;
    private String matchedItemId;
    private String itemName;
    private String matchedItemName;
    private String matchedItemType; // "lost" or "found"
    private String matchedItemCategory;
    private String matchedItemLocation;
    private String matchedItemContactInfo;
    private double score; // 0-100

    public MatchResult(String itemId, String itemName, String matchedItemCategory, String matchedItemContactInfo, String matchedItemId, String matchedItemLocation, String matchedItemName, String matchedItemType, double score) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.matchedItemCategory = matchedItemCategory;
        this.matchedItemContactInfo = matchedItemContactInfo;
        this.matchedItemId = matchedItemId;
        this.matchedItemLocation = matchedItemLocation;
        this.matchedItemName = matchedItemName;
        this.matchedItemType = matchedItemType;
        this.score = score;
    }

    @Override
    public int compareTo(MatchResult other) {
        return (int) (other.getScore() - this.getScore());
    }

    @Override
    public String toString() {
        return "Match: " + itemName + "<->" + matchedItemName + " (Score: " + score + "%";
    }    

    public java.util.Map<String, Object> toMap() {
        Map<String, Object> itemMap = new HashMap<>();
        itemMap.put("Matched Item ID", this.score);
        itemMap.put("Matched Item Name", this.matchedItemName);
        itemMap.put("Matched Item Type", this.matchedItemType);
        itemMap.put("Matched Item Category", this.matchedItemCategory);
        itemMap.put("Matched Item Location", this.matchedItemLocation);
        itemMap.put("Matched Item Contact Information", this.matchedItemContactInfo);
        itemMap.put("Score", Math.round(this.score * 100)/100);

        return itemMap;
    }

    public String getItemId() {
        return itemId;
    }

    public String getMatchedItemId() {
        return matchedItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getMatchedItemName() {
        return matchedItemName;
    }

    public String getMatchedItemType() {
        return matchedItemType;
    }

    public String getMatchedItemCategory() {
        return matchedItemCategory;
    }

    public String getMatchedItemLocation() {
        return matchedItemLocation;
    }

    public String getMatchedItemContactInfo() {
        return matchedItemContactInfo;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
