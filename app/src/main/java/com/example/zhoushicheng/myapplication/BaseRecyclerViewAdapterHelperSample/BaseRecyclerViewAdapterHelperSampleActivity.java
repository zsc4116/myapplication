package com.example.zhoushicheng.myapplication.BaseRecyclerViewAdapterHelperSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.zhoushicheng.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseRecyclerViewAdapterHelperSampleActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ExpandableItemAdapter adapter;
    ArrayList<MultiItemEntity> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_recycler_view_adapter_helper_sample);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);

        list = generateData();
        adapter = new ExpandableItemAdapter(list);

        final GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getItemViewType(position) == ExpandableItemAdapter.TYPE_PERSON ? 1 : manager.getSpanCount();
            }
        });

//        final LinearLayoutManager manager = new LinearLayoutManager(this);

        mRecyclerView.setAdapter(adapter);
        // important! setLayoutManager should be called after setAdapter
        mRecyclerView.setLayoutManager(manager);
        adapter.expandAll();
    }

    private ArrayList<MultiItemEntity> generateData() {
        int lv0Count = 9;
        int lv1Count = 3;
        int personCount = 5;

        String[] nameList = {"Bob", "Andy", "Lily", "Brown", "Bruce"};
        Random random = new Random();

        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < lv0Count; i++) {
            Level0Item lv0 = new Level0Item("This is " + i + "th item in Level 0", "subtitle of " + i);
            for (int j = 0; j < lv1Count; j++) {
                Level1Item lv1 = new Level1Item("Level 1 item: " + j, "(no animation)");
                for (int k = 0; k < personCount; k++) {
                    lv1.addSubItem(new Person(nameList[k], random.nextInt(40)));
                }
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        res.add(new  Level0Item("This is " + lv0Count + "th item in Level 0", "subtitle of " + lv0Count));
        return res;
    }

    public static class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {


        private static final String TAG = ExpandableItemAdapter.class.getSimpleName();

        public static final int TYPE_LEVEL_0 = 0;
        public static final int TYPE_LEVEL_1 = 1;
        public static final int TYPE_PERSON = 2;

        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public ExpandableItemAdapter(List<MultiItemEntity> data) {
            super(data);
            addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
            addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1);
            addItemType(TYPE_PERSON, R.layout.item_expandable_lv2);
        }

        @Override
        protected void convert(final BaseViewHolder holder, MultiItemEntity item) {
            switch (holder.getItemViewType()) {
                case TYPE_LEVEL_0:
                    switch (holder.getLayoutPosition() % 3) {
                        case 0:
                            holder.setImageResource(R.id.iv_head, R.mipmap.head_img0);
                            break;
                        case 1:
                            holder.setImageResource(R.id.iv_head, R.mipmap.head_img1);
                            break;
                        case 2:
                            holder.setImageResource(R.id.iv_head, R.mipmap.head_img2);
                            break;
                    }
                    final Level0Item lv0 = (Level0Item) item;
                    holder.setText(R.id.title, lv0.title)
                            .setText(R.id.sub_title, lv0.subTitle)
                            .setImageResource(R.id.iv, lv0.isExpanded() ? R.mipmap.arrow_b : R.mipmap.arrow_r);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = holder.getAdapterPosition();
                            if (lv0.isExpanded()) {
                                collapse(pos);
                            } else {
                                expand(pos);
                            }
                        }
                    });
                    break;
                case TYPE_LEVEL_1:
                    final Level1Item lv1 = (Level1Item) item;
                    holder.setText(R.id.title, lv1.subTitle)
                            .setText(R.id.sub_title, lv1.subTitle)
                            .setImageResource(R.id.iv, lv1.isExpanded() ? R.mipmap.arrow_b : R.mipmap.arrow_r);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = holder.getAdapterPosition();
                            if (lv1.isExpanded()) {
                                collapse(pos, false);
                            } else {
                                expand(pos, false);
                            }
                        }
                    });

                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            int pos = holder.getAdapterPosition();
                            remove(pos);
                            return true;
                        }
                    });
                    break;
                case TYPE_PERSON:
                    final Person person = (Person) item;
                    holder.setText(R.id.tv, person.name + " parent pos : " + getParentPosition(person));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = holder.getAdapterPosition();
                            remove(pos);
                        }
                    });
                    break;
            }
        }
    }


    public class Level0Item extends AbstractExpandableItem<Level1Item> implements MultiItemEntity {
        public String title;
        public String subTitle;

        public Level0Item(String title, String subTitle) {
            this.subTitle = subTitle;
            this.title = title;
        }

        @Override
        public int getItemType() {
            return ExpandableItemAdapter.TYPE_LEVEL_0;
        }

        @Override
        public int getLevel() {
            return 0;
        }
    }

    public class Level1Item extends AbstractExpandableItem<Person> implements MultiItemEntity {
        public String title;
        public String subTitle;

        public Level1Item(String title, String subTitle) {
            this.subTitle = subTitle;
            this.title = title;
        }

        @Override
        public int getItemType() {
            return ExpandableItemAdapter.TYPE_LEVEL_1;
        }

        @Override
        public int getLevel() {
            return 1;
        }
    }

    public class Person implements MultiItemEntity {
        public Person(String name, int age) {
            this.age = age;
            this.name = name;
        }

        public String name;
        public int age;

        @Override
        public int getItemType() {
            return ExpandableItemAdapter.TYPE_PERSON;
        }
    }
}
