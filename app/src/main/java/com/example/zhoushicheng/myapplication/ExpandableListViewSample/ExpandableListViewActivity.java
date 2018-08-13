package com.example.zhoushicheng.myapplication.ExpandableListViewSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListViewActivity extends AppCompatActivity {

    private List<CommentBean> commentBeans;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view);

        initData();
        initView();
    }

    private void initData() {
        commentBeans = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            List<CommentReplyBean> list = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                list.add(new CommentReplyBean("幻蓝 :", "" + i + " , " + j));
            }
            commentBeans.add(new CommentBean("Tuogusa", "" + i, false, list));
        }
    }

    private void initView() {
        expandableListView = (ExpandableListView) findViewById(R.id.xlv_content);
        initExpandableListView();
    }

    private void initExpandableListView() {
        expandableListView.setGroupIndicator(null);
        final CommentExpandableAdapter adapter = new CommentExpandableAdapter();
        expandableListView.setAdapter(adapter);
        for (int i = 0; i < commentBeans.size(); i++) {
            expandableListView.expandGroup(i);
        }

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (expandableListView.isGroupExpanded(groupPosition)) {
                    expandableListView.collapseGroup(groupPosition);
                } else {
                    expandableListView.expandGroup(groupPosition);
                }

                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                CommentBean bean = commentBeans.get(groupPosition);
                bean.getReplys().add(new CommentReplyBean("史诗级大帅哥", "说得好！"));
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }


    class CommentExpandableAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return commentBeans.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if (commentBeans.get(groupPosition).getReplys() == null) {
                return 0;
            } else {
                return commentBeans.get(groupPosition).getReplys().size() > 0 ?
                        commentBeans.get(groupPosition).getReplys().size() : 0;
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            return commentBeans.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return commentBeans.get(groupPosition).getReplys().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return getCombinedChildId(groupPosition, childPosition);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        boolean isLike = false;

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            final GroupHolder groupHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ExpandableListViewActivity.this)
                        .inflate(R.layout.item_expandable_group, parent, false);
                groupHolder = new GroupHolder(convertView);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }
            groupHolder.tvGroupName.setText(commentBeans.get(groupPosition).getName());
            groupHolder.tvGroupComment.setText(commentBeans.get(groupPosition).getComment());

            groupHolder.imgFavor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isLike) {
                        isLike = false;
                        groupHolder.imgFavor.setImageResource(R.mipmap.pl_red);
                    } else {
                        isLike = true;
                        groupHolder.imgFavor.setImageResource(R.mipmap.pl_blue);
                    }
                }
            });

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final ChildHolder childHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ExpandableListViewActivity.this)
                        .inflate(R.layout.item_expandable_child, parent, false);
                childHolder = new ChildHolder(convertView);
                convertView.setTag(childHolder);
            } else {
                childHolder = (ChildHolder) convertView.getTag();
            }
            childHolder.tvChileName.setText(commentBeans.get(groupPosition)
                    .getReplys().get(childPosition).getName());
            childHolder.tvChildComment.setText(commentBeans.get(groupPosition)
                    .getReplys().get(childPosition).getComment());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    class GroupHolder {
        TextView tvGroupName, tvGroupComment;
        ImageView imgFavor;

        public GroupHolder(View view) {
            tvGroupName = (TextView) view.findViewById(R.id.tv_item_expandable_group_name);
            tvGroupComment = (TextView) view.findViewById(R.id.tv_expandable_group_comment);
            imgFavor = (ImageView) view.findViewById(R.id.img_expandable_group_favor);
        }
    }

    class ChildHolder {
        TextView tvChileName, tvChildComment;

        public ChildHolder(View view) {
            tvChileName = (TextView) view.findViewById(R.id.tv_item_expandable_child_name);
            tvChildComment = (TextView) view.findViewById(R.id.tv_item_expandable_child_comment);
        }
    }
}


