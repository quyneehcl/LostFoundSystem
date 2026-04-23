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
        return Double.compare(other.score, this.score);
    }

    @Override
    public String toString() {
        return "Match: " + itemName + " <-> " + matchedItemName + " (Score: " + score + "%)";
    }    

    public java.util.Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("matchedItemId", matchedItemId);
        map.put("matchedItemName", matchedItemName);
        map.put("matchedItemType", matchedItemType);
        map.put("matchedItemCategory", matchedItemCategory);
        map.put("matchedItemLocation", matchedItemLocation);
        map.put("matchedItemContactInfo", matchedItemContactInfo);
        map.put("score", Math.round(score * 100.0) / 100.0);
        return map;
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
