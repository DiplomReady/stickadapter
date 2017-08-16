package com.codewaves.stickyheadergrid.sample;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.codewaves.sample.R;
import com.codewaves.stickyheadergrid.StickyHeaderAdapter;

import java.util.ArrayList;
import java.util.List;

public class SampleAdapter extends StickyHeaderAdapter {
    private List<TvSection> tvItemSSections;
    private boolean editMode;

    SampleAdapter(List<TvSection> data) {
        addItemsToSection(data);
    }

    private void addItemsToSection(List<TvSection> data) {
        tvItemSSections = new ArrayList<>(data);
    }

    @Override
    public int getSectionCount() {
        return tvItemSSections.size();
    }

    @Override
    public int getSectionItemCount(int section) {
        return tvItemSSections.get(section).getItemsCount();
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_header, parent, false);
        return new MyHeaderViewHolder(view);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_item, parent, false);
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int section) {
        final MyHeaderViewHolder holder = (MyHeaderViewHolder) viewHolder;
        final String label = "Header Number " + section;
        holder.labelView.setText(label);
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, final int section, final int position) {
        final MyItemViewHolder holder = (MyItemViewHolder) viewHolder;
        final TvItem tvItem = tvItemSSections.get(section).getTvItems().get(position);
        holder.labelView.setText(tvItem.getTitle());
        holder.labelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(holder, tvItem);
            }
        });

        holder.checkView.setVisibility(tvItem.isEditMode() ? View.VISIBLE : View.GONE);
        holder.checkView.setChecked(tvItem.isSelected());
        holder.checkView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tvItem.setSelected(isChecked);
            }
        });

     /* if (tvItem.isNeedsDelete()) {
         deleteItem(holder, tvItem);
      }*/

    }

    private void deleteItem(MyItemViewHolder holder, TvItem tvItem) {
        Log.i("***", "deleteItem: ");
        final int section = getAdapterPositionSection(holder.getAdapterPosition());
        final int offset = getItemSectionOffset(section, holder.getAdapterPosition());

        tvItemSSections.get(section).getTvItems().remove(offset);
        notifySectionItemRemoved(section, offset);
        Toast.makeText(holder.labelView.getContext(), tvItem.getTitle(), Toast.LENGTH_SHORT).show();
    }

    public void setEditMode(boolean editMode) {
        for (TvSection tvItemSSection : tvItemSSections) {
            for (TvItem tvItem : tvItemSSection.getTvItems()) {
                tvItem.setEditMode(editMode);
            }
        }

        notifyDataSetChanged();
    }

    public void selectAllItems() {
        for (TvSection tvItemSSection : tvItemSSections) {
            for (TvItem tvItem : tvItemSSection.getTvItems()) {
                tvItem.setSelected(true);
            }
        }

        notifyDataSetChanged();
    }

    public void deleteSelectedItems() {

        List<TvSection> newTvSections = new ArrayList<>();

        for (TvSection tvItemSSection : tvItemSSections) {

            List<TvItem> newTvItems = new ArrayList<>();

            for (TvItem tvItem : tvItemSSection.getTvItems()) {
                if (!tvItem.isSelected()) {
                    newTvItems.add(tvItem);
                }
            }

            if (!newTvItems.isEmpty()) {
                TvSection newTvItemSSection = new TvSection(newTvItems);
                newTvSections.add(newTvItemSSection);
            }

        }

        addItemsToSection(newTvSections);
        recalculateItems();
    }

    public static class MyHeaderViewHolder extends HeaderViewHolder {
        TextView labelView;

        MyHeaderViewHolder(View itemView) {
            super(itemView);
            labelView = (TextView) itemView.findViewById(R.id.label);
        }
    }

    public static class MyItemViewHolder extends ItemViewHolder {
        CheckBox checkView;
        TextView labelView;

        MyItemViewHolder(View itemView) {
            super(itemView);
            labelView = (TextView) itemView.findViewById(R.id.label);
            checkView = (CheckBox) itemView.findViewById(R.id.check);
        }
    }
}
