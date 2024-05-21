package com.mango.artsparkxml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyCardAdapter extends BaseAdapter {

    private Context context;
    private List<CardItem> cardList;

    public MyCardAdapter(Context context, List<CardItem> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @Override
    public int getCount() {
        return cardList.size();
    }

    @Override
    public Object getItem(int position) {
        return cardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.boards, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.boardText1);
        CardItem cardItem = cardList.get(position);
        textView.setText(cardItem.getText());

        return convertView;
    }
}
