package com.example.tongmin.myclassicbluetooth;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

class AdapterAdapter extends BaseAdapter {
    private List<ListAdapter> listAdatper;

    @Override
    public int getItemViewType(int position) {
        int offset = 0;
        int result = 0;
        for (ListAdapter la : listAdatper) {

            int size = la.getCount();
            if (position < size) {
                result = offset + la.getItemViewType(position);
                break;
            }
            position -= size;
            offset += la.getViewTypeCount();
        }
        return result;
    }

    public void addView(View view){
        List<View> listView = new ArrayList<View>(1);
        listView.add(view);
        this.addAdpater(new SackOfViewsAdapter(listView));
        this.notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        int total = 0;

        for (ListAdapter la : listAdatper) {
            total += la.getViewTypeCount();
        }
        return Math.max(total, 1);
    }

    AdapterAdapter() {
        listAdatper = new ArrayList<ListAdapter>();
    }

    void addAdpater(ListAdapter adapter) {
        listAdatper.add(adapter);
        notifyDataSetChanged();
    }
    void addAdpater(ListAdapter adapter , int position) {
        listAdatper.add(position,adapter);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int total = 0;
        for (Adapter a : listAdatper) {
            total += a.getCount();
        }
        return total;
    }

    @Override
    public Object getItem(int position) {
        for (Adapter a : listAdatper) {
            int size = a.getCount();
            if (position < size) {
                return a.getItem(position);
            }
            position -= size;
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        for (Adapter a : listAdatper) {
            int size = a.getCount();
            if (position < size) {
                return a.getItemId(position);
            }
            position -= size;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        for (Adapter a : listAdatper) {
            int size = a.getCount();
            if (position < size) {
                return a.getView(position, convertView, parent);
            }
            position -= size;
        }

        return null;
    }
}