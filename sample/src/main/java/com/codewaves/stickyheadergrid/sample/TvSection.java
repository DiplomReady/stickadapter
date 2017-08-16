package com.codewaves.stickyheadergrid.sample;

import java.util.List;

public class TvSection {

    private List<TvItem> tvItems;

    public TvSection(List<TvItem> tvItems) {
        this.tvItems = tvItems;
    }

    public int getItemsCount(){
        return tvItems.size();
    }


    public List<TvItem> getTvItems() {
        return tvItems;
    }

}
