package com.danieljames.cuber.letters.list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.danieljames.cuber.R;
import com.danieljames.cuber.letters.LettersModel;

public class LettersListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    public LettersModel lettersModel;

    public LettersListAdapter(Activity activity) {
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lettersModel.keyList.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.letters_list_row, null);
        }
        TextView valueTextView = (TextView) view.findViewById(R.id.letters_list_value);
        valueTextView.setText(lettersModel.valueList[position]);
        TextView keyTextView = (TextView) view.findViewById(R.id.letters_list_key);
        keyTextView.setText(lettersModel.keyList[position]);
        return view;
    }
}
