package com.example.project_2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.List;

public class ItemAdapter extends BaseAdapter {

    private List<ItemType> data;

    private Context context;
    private int resourceId;
    private GridView gridView;
    private String myString;


    public ItemAdapter(Context context, int resourceId, GridView gridView, List<ItemType> objects,String myString) {
        this.context = context;
        this.resourceId = resourceId;
        this.gridView = gridView;
        this.data = objects;
        this.myString = myString;
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        ItemType type = (ItemType) getItem(position);
        View view = LayoutInflater.from(context).inflate(resourceId, null);
        View displayItemView = view.findViewById(R.id.displayItemView);
        LinearLayout displayItemLayout = view.findViewById(R.id.layout);


        int color = Color.parseColor("#ffffff");
        switch (type) {
            case EMPTY:
                color = Color.parseColor("#ffffff");
                break;
            case SNAKE:
                if(myString.equals("mainland")){
                    color = Color.parseColor("#008454");
                }
                if(myString.equals("taiwan")){
                    color = Color.parseColor("#DF8E00");
                }
                if(myString.equals("hongkong")){
                    color = Color.parseColor("#209881");
                }
                if(myString.equals("macao")){
                    color = Color.parseColor("#77B8CB");
                }
                break;
            case FOOD:
                if(myString.equals("mainland")){
                    color = Color.parseColor("#008454");
                }
                if(myString.equals("taiwan")){
                    color = Color.parseColor("#DF8E00");
                }
                if(myString.equals("hongkong")){
                    color = Color.parseColor("#209881");
                }
                if(myString.equals("macao")){
                    color = Color.parseColor("#77B8CB");
                }
                break;
        }

        int radius = gridView.getWidth() / 15;

        LinearLayout.LayoutParams parmas = new LinearLayout.LayoutParams(radius, radius);
        displayItemLayout.setLayoutParams(parmas);

        displayItemView.setBackgroundColor(color);
        return view;
    }
}
