package com.codewaves.stickyheadergrid.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codewaves.sample.R;
import com.codewaves.stickyheadergrid.StickyHeaderLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends AppCompatActivity {
   private static final int SPAN_SIZE = 1;
   private static final int SECTIONS = 13;
   private static final int SECTION_ITEMS = 1;

   private RecyclerView mRecycler;
   private StickyHeaderLayoutManager mLayoutManager;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_sample);

      // Setup recycler
      mRecycler = (RecyclerView)findViewById(R.id.recycler);
      mLayoutManager = new StickyHeaderLayoutManager(SPAN_SIZE);
      mLayoutManager.setHeaderBottomOverlapMargin(getResources().getDimensionPixelSize(R.dimen.header_shadow_size));
      mLayoutManager.setSpanSizeLookup(new StickyHeaderLayoutManager.SpanSizeLookup() {
         @Override
         public int getSpanSize(int section, int position) {
            switch (section) {
               case 0:
                  return 3;
               case 1:
                  return 1;
               case 2:
                  return 3 - position % 3;
               case 3:
                  return position % 2 + 1;
               default:
                  return 1;
            }
         }
      });

      // Workaround RecyclerView limitation when playing remove animations. RecyclerView always
      // puts the removed item on the top of other views and it will be drawn above sticky header.
      // The only way to fix this, abandon remove animations :(
      mRecycler.setItemAnimator(new DefaultItemAnimator() {
         @Override
         public boolean animateRemove(RecyclerView.ViewHolder holder) {
            dispatchRemoveFinished(holder);
            return false;
         }
      });
      mRecycler.setLayoutManager(mLayoutManager);
//      mRecycler.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));

      List<TvSection> dataForHeader = new ArrayList<>();
      dataForHeader.add(new TvSection(new ArrayList<TvItem>(2)));
      dataForHeader.add(new TvSection(new ArrayList<TvItem>(6)));
      dataForHeader.add(new TvSection(new ArrayList<TvItem>(1)));
      dataForHeader.add(new TvSection(new ArrayList<TvItem>(2)));
      dataForHeader.add(new TvSection(new ArrayList<TvItem>(5)));
      dataForHeader.add(new TvSection(new ArrayList<TvItem>(1)));
      dataForHeader.add(new TvSection(new ArrayList<TvItem>(3)));
      dataForHeader.add(new TvSection(new ArrayList<TvItem>(9)));
      dataForHeader.add(new TvSection(new ArrayList<TvItem>(4)));
      dataForHeader.add(new TvSection(new ArrayList<TvItem>(1)));
      final SampleAdapter adapter = new SampleAdapter(dataForHeader);
      mRecycler.setAdapter(adapter);

      findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            adapter.setEditMode(true);
         }
      });

      findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            adapter.setEditMode(false);
         }
      });

      findViewById(R.id.select_all).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            adapter.selectAllItems();
         }
      });

      findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            adapter.deleteSelectedItems();
         }
      });

   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
      switch (id) {
         case R.id.action_top:
            mRecycler.scrollToPosition(0);
            break;
         case R.id.action_center:
            mRecycler.scrollToPosition(mRecycler.getAdapter().getItemCount() / 2);
            break;
         case R.id.action_bottom:
            mRecycler.scrollToPosition(mRecycler.getAdapter().getItemCount() - 1);
            break;
         case R.id.action_top_smooth:
            mRecycler.smoothScrollToPosition(0);
            break;
         case R.id.action_center_smooth:
            mRecycler.smoothScrollToPosition(mRecycler.getAdapter().getItemCount() / 2);
            break;
         case R.id.action_bottom_smooth:
            mRecycler.smoothScrollToPosition(mRecycler.getAdapter().getItemCount() - 1);
            break;
      }
      return super.onOptionsItemSelected(item);

   }
}
