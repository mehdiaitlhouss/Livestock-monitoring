package com.miola.livestockmonitoring;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.miola.livestockmonitoring.DRVInterface.LoadMore;
import com.miola.livestockmonitoring.model.DynamicRVAdapter;
import com.miola.livestockmonitoring.model.DynamicRVModel;
import com.miola.livestockmonitoring.model.StaticRvAdapter;
import com.miola.livestockmonitoring.model.StaticRvModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListAnimals extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StaticRvAdapter staticRvAdapter;
    private DynamicRVAdapter dynamicRVAdapter;

    private List<DynamicRVModel> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_animals);

        ArrayList<StaticRvModel> item = new ArrayList<>();
        item.add(new StaticRvModel(R.drawable.cow_icon, "cow"));
        item.add(new StaticRvModel(R.drawable.cow_icon, "cow"));
        item.add(new StaticRvModel(R.drawable.cow_icon, "cow"));
        item.add(new StaticRvModel(R.drawable.cow_icon, "cow"));
        item.add(new StaticRvModel(R.drawable.cow_icon, "cow"));
        item.add(new StaticRvModel(R.drawable.cow_icon, "cow"));
        item.add(new StaticRvModel(R.drawable.cow_icon, "cow"));

        recyclerView = findViewById(R.id.rv_1);
        staticRvAdapter = new StaticRvAdapter(item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(staticRvAdapter);

        items.add(new DynamicRVModel("Cow"));
        items.add(new DynamicRVModel("Cow"));
        items.add(new DynamicRVModel("Cow"));
        items.add(new DynamicRVModel("Cow"));
        items.add(new DynamicRVModel("Cow"));
        items.add(new DynamicRVModel("Cow"));
        items.add(new DynamicRVModel("Cow"));
        items.add(new DynamicRVModel("Cow"));
        items.add(new DynamicRVModel("Cow"));
        items.add(new DynamicRVModel("Cow"));
        items.add(new DynamicRVModel("Cow"));
        items.add(new DynamicRVModel("Cow"));
        items.add(new DynamicRVModel("Cow"));
        items.add(new DynamicRVModel("Cow"));
        items.add(new DynamicRVModel("Cow"));
        items.add(new DynamicRVModel("Cow"));
        items.add(new DynamicRVModel("Cow"));


        RecyclerView drv = findViewById(R.id.rv_2);
        drv.setLayoutManager(new LinearLayoutManager(this));
        dynamicRVAdapter = new DynamicRVAdapter(drv, this, items);
        drv.setAdapter(dynamicRVAdapter);
        dynamicRVAdapter.setLoadMore(new LoadMore() {
            @Override
            public void onLoadMore() {

                if (items.size() < -10) {
                    item.add(null);
                    dynamicRVAdapter.notifyItemInserted(items.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            items.remove(items.size() - 1);
                            dynamicRVAdapter.notifyItemRemoved(items.size());
                            int index = items.size();
                            int end = index + 10;

                            for (int i = index; i < end; i++) {
                                String name = UUID.randomUUID().toString();
                                DynamicRVModel item = new DynamicRVModel(name);
                                items.add(item);
                            }
                            dynamicRVAdapter.notifyDataSetChanged();
                            dynamicRVAdapter.setLoaded();
                        }
                    }, 4000);
                }
                else {
                    Toast.makeText(ListAnimals.this, "Data Completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}