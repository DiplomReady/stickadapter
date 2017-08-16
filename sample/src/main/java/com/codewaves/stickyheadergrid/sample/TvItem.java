package com.codewaves.stickyheadergrid.sample;

public class TvItem {

    private int listingCount;

    private boolean isSelected;
    private boolean isEditMode;
    private boolean isNeedsDelete;
    private String title;

    public TvItem(int listingCount) {
        this.listingCount = listingCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getListingCount() {
        return listingCount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;

        if (!editMode) {
            isSelected = false;
        }
    }

    public boolean isNeedsDelete() {
        return isNeedsDelete;
    }

    public void setNeedsDelete(boolean needsDelete) {
        isNeedsDelete = needsDelete;
    }
}
