package com.lostfound.demo.services;

import com.lostfound.demo.models.Item;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class ItemCleaner {

    public PriorityQueue<Item> buildMinHeapByDate(List<Item> items) {
        PriorityQueue<Item> minHeap = new PriorityQueue<>(
                Comparator.comparing(Item::getDate)
        );

        for (Item item : items) {
            minHeap.add(item);
        }

        return minHeap;
    }


    public List<Item> removeOldReports(PriorityQueue<Item> heap) {
        LocalDate cutoffDate = LocalDate.now().minusDays(30);
        List<Item> removed = new java.util.ArrayList<>();

  
        while (!heap.isEmpty() && heap.peek().getDate().isBefore(cutoffDate)) {
            removed.add(heap.poll());
        }

        return removed;
    }
}