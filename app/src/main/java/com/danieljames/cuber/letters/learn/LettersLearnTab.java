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
        Button reset = (Button) view.findViewById(R.id.letters_reset);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLearning();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetLearning();
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
        int i = 0;
        while (i < count) {
            int randRowInt = rand.nextInt(lettersModel.keyList.length);
            keys[i] = lettersModel.keyList[randRowInt];
            values[i] = lettersModel.valueList[randRowInt];
            boolean found = false;
            for (int j = 0; j < i; j++) {
                if (keys[j] == keys[i]) {
                    found = true;
                    break;
                }
            }
            if (lettersModel.pointList[randRowInt] != 5 && !found) {
                i++;
            }
        }
        Intent intent;
        intent = new Intent(getActivity(), LettersLearnActivity.class);
        intent.putExtra("keys", keys);
        intent.putExtra("values", values);
        startActivity(intent);
    }

    private void resetLearning() {
        lettersModel.reset();
    }
}
