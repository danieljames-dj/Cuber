package com.danieljames.cuber.letters.learn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.danieljames.cuber.R;
import com.danieljames.cuber.letters.LettersModel;

import java.io.Serializable;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LettersLearnTab extends Fragment {

    public LettersModel lettersModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.letters_learn_tab, container, false);
        Button button = (Button) view.findViewById(R.id.letters_start_learn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLearning();
            }
        });
        return view;
    }

    private void startLearning() {
        EditText editText = (EditText) getActivity().findViewById(R.id.letters_learn_count);
        int count = Integer.valueOf(editText.getText().toString());
        String[] keys = new String[count];
        String[] values = new String[count];
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int randRowInt = rand.nextInt(lettersModel.keyList.length);
            keys[i] = lettersModel.keyList[randRowInt];
            values[i] = lettersModel.valueList[randRowInt];
        }
        Intent intent;
        intent = new Intent(getActivity(), LettersLearnActivity.class);
        intent.putExtra("keys", keys);
        intent.putExtra("values", values);
        startActivity(intent);
    }
}
