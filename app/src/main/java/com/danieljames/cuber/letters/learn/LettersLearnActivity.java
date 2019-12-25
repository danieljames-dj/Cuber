package com.danieljames.cuber.letters.learn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.danieljames.cuber.R;
import com.danieljames.cuber.letters.LettersModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LettersLearnActivity extends AppCompatActivity {

    String[] keys, values;
    TextView letterPair, expansion;
    Button showExpansion;
    Button[] ratings = new Button[5];
    int currentIndex = 0;
    int totalRating = 0;
    public LettersModel lettersModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.letters_learn_activity);
        keys = getIntent().getStringArrayExtra("keys");
        values = getIntent().getStringArrayExtra("values");
        lettersModel = LettersModel.lettersModel;
        letterPair = findViewById(R.id.learn_activity_letterpair);
        expansion = findViewById(R.id.learn_activity_expansion);
        showExpansion = findViewById(R.id.learn_activity_show_expansion);
        final Activity context = this;
        int[] ratingIds = {R.id.learn_activity_rating1,
                R.id.learn_activity_rating2,
                R.id.learn_activity_rating3,
                R.id.learn_activity_rating4,
                R.id.learn_activity_rating5
        };
        for (int i = 0; i < 5; i++) {
            final int currentRating = i + 1;
            ratings[i] = findViewById(ratingIds[i]);
            ratings[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    totalRating += currentRating;
                    currentIndex += 1;
                    if (currentIndex < keys.length) {
                        expansion.setText("");
                        letterPair.setText(keys[currentIndex]);
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Finished");

                        final TextView output = new TextView(context);
                        output.setText(String.valueOf(totalRating*20/keys.length));
                        alert.setView(output);

                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                context.finish();
                            }
                        });

                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Canceled.
                            }
                        });

                        alert.show();
                    }
                }
            });
        }
        showExpansion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expansion.setText(values[currentIndex]);
            }
        });
        expansion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editKey(context);
            }
        });
        letterPair.setText(keys[currentIndex]);
    }

    private void editKey(final Activity context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(keys[currentIndex]);

        final EditText input = new EditText(context);
        input.setText(values[currentIndex]);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                lettersModel.updatePair(keys[currentIndex], input.getText().toString());
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
