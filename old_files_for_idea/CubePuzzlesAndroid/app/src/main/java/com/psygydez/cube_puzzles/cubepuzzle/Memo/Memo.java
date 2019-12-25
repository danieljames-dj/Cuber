package com.psygydez.cube_puzzles.cubepuzzle.Memo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.psygydez.cube_puzzles.cubepuzzle.R;

/**
 * Created by danieljames on 3/17/18.
 */

public class Memo extends Fragment {

    public Memo() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo, container, false);
        ListView listView = (ListView) view.findViewById(R.id.memo_options);
        String[] values = getResources().getStringArray(R.array.memo_menu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(getActivity(), LetterPairs.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        return view;
    }
}
