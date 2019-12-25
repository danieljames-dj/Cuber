package com.psygydez.cube_puzzles.cubepuzzle.Timer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.psygydez.cube_puzzles.cubepuzzle.R;

/**
 * Created by danieljames on 3/17/18.
 */

public class TimeRecorder extends Fragment {

    TextView time;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long updatedTime = 0L;
    int stage = 0;
    /*
    * Ideal stage = 0
    * Time going to start = 1
    * Timer ready = 2
    * Time started = 3
    * Time stopped = 0*/

    public TimeRecorder() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        time = (TextView)view.findViewById(R.id.time);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (stage == 0) {
                            time.setTextColor(Color.RED);
                            startTime = SystemClock.uptimeMillis();
                            customHandler.postDelayed(updateTimerThread, 0);
                            stage = 1;
                        } else if (stage == 3) {
                            time.setTextColor(Color.GRAY);
                            stage = 0;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (stage == 2) {
                            startTime = SystemClock.uptimeMillis();
                            time.setTextColor(Color.BLUE);
                            stage = 3;
                        } else {
                            time.setTextColor(Color.GRAY);
                            stage = 0;
                        }
                        break;
                }
                return true;
            }
        });
        View fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setCancelable(false);
                alertDialog.setTitle("Please wait for few days, we will add more features soon... :-)");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        return view;
    }

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeInMilliseconds;
            if (stage == 1 && updatedTime >= 1000) {
                time.setTextColor(Color.GREEN);
                stage = 2;
            } else if (stage == 3) {
                int ms = (int) (updatedTime % 1000);
                int secs = (int) (updatedTime / 1000);
                int mins = secs / 60;
                secs = secs % 60;
                if (mins == 0) time.setText("" + secs + "." + String.format("%03d", ms));
                else if (mins < 10) time.setText("" + mins + ":" + String.format("%02d", secs) + "." + String.format("%03d", ms));
                else time.setText("" + mins + ":" + String.format("%02d", secs));
            }
            customHandler.postDelayed(this, 0);
        }
    };
}