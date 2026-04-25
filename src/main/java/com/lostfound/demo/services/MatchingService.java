package com.lostfound.demo.services;

import com.lostfound.demo.models.FoundItem;
import com.lostfound.demo.models.MatchResult;
import com.lostfound.demo.models.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


public class MatchingService {
    public List<MatchResult> findMatches(FoundItem foundItem, List<Item> allItems){
        PriorityQueue<MatchResult> pq = new PriorityQueue<>();

        for(Item item : allItems){
            if (item.getId().equals(foundItem.getId())) continue;

            if(item.getItemType().equalsIgnoreCase("lost") && item.getStatus().equalsIgnoreCase("active")){
                double score = calculateScore(foundItem, item);

                if(score > 0){
                    pd.add(new MatchResult(
                        lostItem.getId(), // itemId (source)
                        item.getId(), // matchedItemId
                        lostItem.getName(), // itemName
                        item.getName(), // matchedItemName
                        item.getItemType(), // matchedItemType
                        item.getCategory(),  // matchedItemCategory
                        item.getLocation(), // matchedItemLocation
                        item.getContactInfo(), // matchedItemContactInfo
                        score
                    ))
                }
                
        }
         List<MatchResult> results = new ArrayList<>();
        while (!pq.isEmpty()) {
            results.add(pq.poll());
        }

        return results;
    }

    public List<MatchResult> findMatchesForLost(FoundItem foundItem, List<Item> allItems){
        PriorityQueue<MatchResult> pq = new PriorityQueue<>();

        for(Item item : allItems){
            if (item.getId().equals(foundItem.getId())) continue;

            if(item.getItemType().equalsIgnoreCase("lost") && item.getStatus().equalsIgnoreCase("active")){
                double score = calculateScore(foundItem, item);

                if(score > 0){
                    pd.add(new MatchResult(
                        lostItem.getId(), // itemId (source)
                        item.getId(), // matchedItemId
                        lostItem.getName(), // itemName
                        item.getName(), // matchedItemName
                        item.getItemType(), // matchedItemType
                        item.getCategory(),  // matchedItemCategory
                        item.getLocation(), // matchedItemLocation
                        item.getContactInfo(), // matchedItemContactInfo
                        score
                    ))
                }
                
        }
        List<MatchResult> results = new ArrayList<>();
        while (!pq.isEmpty()) {
            results.add(pq.poll());
        }

        return results;
    
    public double calculateScore(FoundItem foundItem, Item item){
           if (item.getCategory() != null && foundItem.getCategory() != null &&
                    item.getCategory().equalsIgnoreCase(foundItem.getCategory())) {
                            score += 40;
                }
                score += stringSimilarity(foundItem.getLocation(), item.getLocation()) * 30;

                score += stringSimilarity(foundItem.getName(), item.getName()) * 20;

                score += stringSimilarity(foundItem.getDescription(), item.getDescription()) * 10;

            }
    }

    public double stringSimilarity(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return 0.0;
        }

        s1 = s1.trim().toLowerCase();
        s2 = s2.trim().toLowerCase();

        if (s1.isEmpty() || s2.isEmpty()) {
            return 0.0;
        }

        if (s1.equals(s2)) {
            return 1.0;
        }

        if (s1.contains(s2) || s2.contains(s1)) {
            return 0.7;
        }

        String[] words1 = s1.split("\\s+");
        String[] words2 = s2.split("\\s+");

        int overlap = 0;
        boolean[] matched = new boolean[words2.length];

        for (String w1 : words1) {
            for (int i = 0; i < words2.length; i++) {
                if (!matched[i] && w1.equals(words2[i])) {
                    overlap++;
                    matched[i] = true;
                    break;
                }
            }
        }

        int maxWords = Math.max(words1.length, words2.length);
        if (maxWords == 0) {
            return 0.0;
        }

        return ((double) overlap / maxWords) * 0.5;
    }

}
