package com.codewaves.stickyheadergrid.sample;

import java.util.ArrayList;
import java.util.List;

public class TvSection {

    private List<TvItem> tvItems;

    public TvSection(int itemsCount) {
        tvItems = new ArrayList<>(itemsCount);
        for (int i = 0; i < itemsCount; i++) {
            tvItems.add(new TvItem("Item " + i));
        }
    }

    public TvSection(List<TvItem> itemsCount) {
        tvItems = new ArrayList<>(itemsCount);
    }

    public int getItemsCount(){
        return tvItems.size();
    }


    public List<TvItem> getTvItems() {
        return tvItems;
    }

}
