package com.example.zhoushicheng.myapplication.ViewDragHelperSample;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;


public class LeftMenuFragment extends ListFragment {

    private static final int SIZE_MENU_ITEM = 3;

    private MenuItem[] mItems = new MenuItem[SIZE_MENU_ITEM];

    private LeftMenuAdapter mAdapter;

    private LayoutInflater mInflater;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInflater = LayoutInflater.from(getActivity());

        MenuItem menuItem = null;
        for (int i = 0; i < SIZE_MENU_ITEM; i++) {
            menuItem = new MenuItem(getResources().getStringArray(R.array.array_left_menu)[i], false, R.mipmap.pl_blue, R.mipmap.pl_red);
            mItems[i] = menuItem;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(0xffffffff);
        setListAdapter(mAdapter = new LeftMenuAdapter(getActivity(), mItems));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (mMenuItemSelectedListener != null) {
            mMenuItemSelectedListener.menuItemSelected(((MenuItem) getListAdapter().getItem(position)).text);
        }
        mAdapter.setSelected(position);
    }

    public interface OnMenuItemSelectedListener {
        void menuItemSelected(String title);
    }

    private OnMenuItemSelectedListener mMenuItemSelectedListener;

    public void setMenuItemSelectedListener(OnMenuItemSelectedListener menuItemSelectedListener) {
        this.mMenuItemSelectedListener = menuItemSelectedListener;
    }
}

class LeftMenuAdapter extends ArrayAdapter<MenuItem> {
    private LayoutInflater mInflater;
    private int mSelected;

    public LeftMenuAdapter(@NonNull Context context, MenuItem[] items) {
        super(context, -1, items);

        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_left_menu, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_item_icon);
        TextView textView = (TextView) convertView.findViewById(R.id.tv_item_title);
        textView.setText(getItem(position).text);
        imageView.setImageResource(getItem(position).icon);
        convertView.setBackgroundColor(Color.TRANSPARENT);

        if (position == mSelected) {
            imageView.setImageResource(getItem(position).iconSelected);
            convertView.setBackgroundColor(Color.MAGENTA);
        }

        return convertView;
    }

    public void setSelected(int position) {
        this.mSelected = position;
        notifyDataSetChanged();
    }
}

class MenuItem {
    String text;
    boolean isSelecte;
    int icon;
    int iconSelected;

    public MenuItem(String text, boolean isSelecte, int icon, int iconSelected) {
        this.text = text;
        this.isSelecte = isSelecte;
        this.icon = icon;
        this.iconSelected = iconSelected;
    }
}
