package com.danieljames.cuber.letters.list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.danieljames.cuber.R;
import com.danieljames.cuber.letters.LettersModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LettersListTab extends Fragment {

    public LettersModel lettersModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.letters_list_tab, container, false);
        ListView listView = (ListView) view.findViewById(R.id.letters_list);
        LettersListAdapter adapter = new LettersListAdapter(getActivity());
        adapter.lettersModel = lettersModel;
        listView.setAdapter(adapter);
        adapter.lettersModel = this.lettersModel;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView keyTextView = (TextView) view.findViewById(R.id.letters_list_key);
                TextView valueTextView = (TextView) view.findViewById(R.id.letters_list_value);
                updateRow(keyTextView.getText().toString(), valueTextView.getText().toString());
            }
        });
        return view;
    }

    void updateRow(final String key, String value) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this.getContext());
        alert.setTitle(key);

        final EditText input = new EditText(this.getContext());
        input.setText(value);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                lettersModel.updatePair(key, input.getText().toString());
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
        input.requestFocus();
    }
}
