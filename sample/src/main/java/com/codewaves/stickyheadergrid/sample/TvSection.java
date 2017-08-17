package com.codewaves.stickyheadergrid.sample;

import java.util.ArrayList;
import java.util.List;

public class TvSection {

    private List<TvItem> tvItems;
    private String title;

    public TvSection(String title, int itemsCount) {
        tvItems = new ArrayList<>(itemsCount);
        this.title = title;
        for (int i = 0; i < itemsCount; i++) {
            tvItems.add(new TvItem("Item " + i));
        }
    }

    public TvSection(String title, List<TvItem> itemsCount) {
        this.title = title;
        tvItems = new ArrayList<>(itemsCount);
    }

    public int getItemsCount(){
        return tvItems.size();
    }

    public String getTitle() {
        return title;
    }


    public List<TvItem> getTvItems() {
        return tvItems;
    }

}
