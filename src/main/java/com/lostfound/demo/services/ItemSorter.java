package com.lostfound.demo.services;
import com.lostfound.demo.models.Item;
import java.util.ArrayList;
import java.util.List;

/**
 * ItemSorter - Sort items by date.
 * @author Ngu Hoang Nguyen
 */

public class ItemSorter {
    // Merge Sort to sort items by date (newest first)
    public static void mergeSortByDateDesc(List<Item> list, int left, int right) {
        if (list == null || list.size() < 2 || left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        mergeSortByDateDesc(list, left, mid);
        mergeSortByDateDesc(list, mid + 1, right);
        merge(list, left, mid, right);
    }
    // Merge algorithm to merge two sorted halves
    private static void merge(List<Item> list, int left, int mid, int right) {
        List<Item> temp = new ArrayList<>();
        int i = left;
        int j = mid + 1;

        while (i <= mid && j <= right) {
            Item leftItem = list.get(i);
            Item rightItem = list.get(j);

            if (leftItem.getDate().isAfter(rightItem.getDate())
                    || leftItem.getDate().isEqual(rightItem.getDate())) {
                temp.add(leftItem);
                i++;
            } else {
                temp.add(rightItem);
                j++;
            }
        }
        while (i <= mid) {
            temp.add(list.get(i));
            i++;
        }
        while (j <= right) {
            temp.add(list.get(j));
            j++;
        }

        for (int k = 0; k < temp.size(); k++) {
            list.set(left + k, temp.get(k));
        }
    }
}
