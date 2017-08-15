package com.codewaves.stickyheadergrid.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codewaves.sample.R;
import com.codewaves.stickyheadergrid.StickyHeaderGridAdapter;

import java.util.ArrayList;
import java.util.List;

public class SampleAdapter extends StickyHeaderGridAdapter {
   private List<List<String>> labels;

   SampleAdapter(List<Integer> data) {
      labels = new ArrayList<>(data.size());
      for (int s = 0; s < data.size(); ++s) {
         List<String> labels = new ArrayList<>(data.get(s));
         for (int i = 0; i < data.get(s); ++i) {
            String label = "Item " + String.valueOf(i);
            labels.add(label);
         }
         this.labels.add(labels);
      }
   }

   @Override
   public int getSectionCount() {
      return labels.size();
   }

   @Override
   public int getSectionItemCount(int section) {
      return labels.get(section).size();
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
      final MyHeaderViewHolder holder = (MyHeaderViewHolder)viewHolder;
      final String label = "Header Number " + section;
      holder.labelView.setText(label);
   }

   @Override
   public void onBindItemViewHolder(ItemViewHolder viewHolder, final int section, final int position) {
      final MyItemViewHolder holder = (MyItemViewHolder)viewHolder;
      final String label = labels.get(section).get(position);
      holder.labelView.setText(label);
      holder.labelView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            final int section = getAdapterPositionSection(holder.getAdapterPosition());
            final int offset = getItemSectionOffset(section, holder.getAdapterPosition());

            labels.get(section).remove(offset);
            notifySectionItemRemoved(section, offset);
            Toast.makeText(holder.labelView.getContext(), label, Toast.LENGTH_SHORT).show();
         }
      });
   }

   public static class MyHeaderViewHolder extends HeaderViewHolder {
      TextView labelView;

      MyHeaderViewHolder(View itemView) {
         super(itemView);
         labelView = (TextView) itemView.findViewById(R.id.label);
      }
   }

   public static class MyItemViewHolder extends ItemViewHolder {
      TextView labelView;

      MyItemViewHolder(View itemView) {
         super(itemView);
         labelView = (TextView) itemView.findViewById(R.id.label);
      }
   }
}
