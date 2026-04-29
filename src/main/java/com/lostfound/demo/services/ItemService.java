package com.lostfound.demo.services;

import com.lostfound.demo.models.FoundItem;
import com.lostfound.demo.models.Item;
import com.lostfound.demo.models.LostItem;
import com.lostfound.demo.models.MatchResult;
import com.lostfound.demo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Connects ItemController to ItemRepository and coordinates ItemSearcher, ItemSorter, MatchingService, and ItemCleaner
 * @author Nguyen Minh Quyen
 */
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    private final ItemSearcher searcher   = new ItemSearcher();
    private final MatchingService matcher = new MatchingService();
    private final ItemCleaner cleaner     = new ItemCleaner();

    public Item addItem(Item item) {
        if (item.getId() == null) {
            item.setId(UUID.randomUUID().toString());
        }
        itemRepository.addItem(item);
        return item;
    }

    public List<Item> getAllItems() {
        List<Item> items = itemRepository.getAllItems();
        if (items.size() > 1) {
            ItemSorter.mergeSortByDateDesc(items, 0, items.size() - 1);
        }
        return items;
    }

    public Item getItemById(String id) {
        return searcher.findById(itemRepository.getAllItems(), id);
    }

    public boolean deleteItem(String id) {
        return itemRepository.deleteItem(id);
    }

    public boolean markAsReturned(String id) {
        return itemRepository.updateItemStatus(id, "returned");
    }

    // Search

    public List<Item> searchItems(String keyword) {
        List<Item> all    = itemRepository.getAllItems();
        List<Item> result = searcher.searchByKeyword(all, keyword);
        if (result.size() > 1) {
            ItemSorter.mergeSortByDateDesc(result, 0, result.size() - 1);
        }
        return result;
    }

    // Matching
    public List<MatchResult> findMatchesById(String itemId) {
        List<Item> all = itemRepository.getAllItems();
        Item item      = searcher.findById(all, itemId);

        if (item instanceof FoundItem) {
            return matcher.findMatches((FoundItem) item, all);
        } else if (item instanceof LostItem) {
            return matcher.findMatchesForLost((LostItem) item, all);
        }
        return new ArrayList<>();
    }

    // Clean Old Reports
    public String cleanOldReports() {
        List<Item> all     = itemRepository.getAllItems();
        var heap           = cleaner.buildMinHeapByDate(all);
        List<Item> removed = cleaner.removeOldReports(heap);

        for (Item item : removed) {
            itemRepository.deleteItem(item.getId());
        }

        return "Removed " + removed.size() + " old reports.";
    }
}