package com.lostfound.demo.services;

import com.lostfound.demo.models.FoundItem;
import com.lostfound.demo.models.Item;
import com.lostfound.demo.models.LostItem;
import com.lostfound.demo.models.MatchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Core component responsible for matching lost and found items.
 * Uses a weighted scoring system based on four criteria:
 *   - Category (40%)
 *   - Location (30%)
 *   - Name (20%)
 *   - Description (10%)
 * Results are ranked using a PriorityQueue (max-heap)
 *
 * @author Nguyen The Minh Duc
 */
public class MatchingService {

    // Find matching lost items for a given found item
    public List<MatchResult> findMatches(FoundItem foundItem, List<Item> allItems) {
        PriorityQueue<MatchResult> maxHeap = new PriorityQueue<>();
        String targetId = foundItem.getId();

        for (Item item : allItems) {
            if (item.getId().equals(targetId)) continue;
            if (!item.getItemType().equals("lost")) continue;
            if (!item.getStatus().equals("active")) continue;

            double score = calculateScore(
                    foundItem.getCategory(), item.getCategory(),
                    foundItem.getLocation(), item.getLocation(),
                    foundItem.getName(), item.getName(),
                    foundItem.getDescription(), item.getDescription());

            if (score > 0) {
                maxHeap.add(new MatchResult(
                        targetId, item.getId(),
                        foundItem.getName(), item.getName(),
                        item.getItemType(), item.getCategory(),
                        item.getLocation(), item.getContactInfo(),
                        score));
            }
        }

        List<MatchResult> results = new ArrayList<>();
        while (!maxHeap.isEmpty()) results.add(maxHeap.poll());
        return results;
    }

    // Find matching found items for a given lost item.
    public List<MatchResult> findMatchesForLost(LostItem lostItem, List<Item> allItems) {
        PriorityQueue<MatchResult> maxHeap = new PriorityQueue<>();
        String targetId = lostItem.getId();

        for (Item item : allItems) {
            if (item.getId().equals(targetId)) continue;
            if (!item.getItemType().equals("found")) continue;
            if (!item.getStatus().equals("active")) continue;

            double score = calculateScore(
                    lostItem.getCategory(), item.getCategory(),
                    lostItem.getLocation(), item.getLocation(),
                    lostItem.getName(), item.getName(),
                    lostItem.getDescription(), item.getDescription());

            if (score > 0) {
                maxHeap.add(new MatchResult(
                        targetId, item.getId(),
                        lostItem.getName(), item.getName(),
                        item.getItemType(), item.getCategory(),
                        item.getLocation(), item.getContactInfo(),
                        score));
            }
        }

        List<MatchResult> results = new ArrayList<>();
        while (!maxHeap.isEmpty()) results.add(maxHeap.poll());
        return results;
    }

    /**
     * Calculate weighted similarity score between two items.
     * Category (40%), Location (30%), Name (20%), Description (10%)
     */
    private double calculateScore(String catA, String catB,
                                   String locA, String locB,
                                   String nameA, String nameB,
                                   String descA, String descB) {
        double score = 0;
        if (catA != null && catB != null && catA.equalsIgnoreCase(catB)) score += 40;
        score += stringSimilarity(locA, locB) * 30;
        score += stringSimilarity(nameA, nameB) * 20;
        score += stringSimilarity(descA, descB) * 10;
        return Math.round(score * 100.0) / 100.0;
    }

    // String similarity: exact match (1.0), contains (0.7), word overlap (0.5 max)
    private double stringSimilarity(String a, String b) {
        if (a == null || b == null) return 0;
        if (a.trim().isEmpty() || b.trim().isEmpty()) return 0;

        String lowerA = a.toLowerCase().trim();
        String lowerB = b.toLowerCase().trim();

        if (lowerA.equals(lowerB)) return 1.0;
        if (lowerA.contains(lowerB) || lowerB.contains(lowerA)) return 0.7;

        String[] wordsA = lowerA.split("\\s+");
        String[] wordsB = lowerB.split("\\s+");

        int overlap = 0;
        for (String wordA : wordsA) {
            for (String wordB : wordsB) {
                if (wordA.equals(wordB)) { overlap++; break; }
            }
        }

        int maxWords = Math.max(wordsA.length, wordsB.length);
        return maxWords > 0 ? ((double) overlap / maxWords) * 0.5 : 0;
    }
}
