package com.psygydez.cube_puzzles.cubepuzzle;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.psygydez.cube_puzzles.cubepuzzle.Memo.Memo;
import com.psygydez.cube_puzzles.cubepuzzle.Timer.TimeRecorder;

/**
 * Created by danieljames on 3/17/18.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private int pageCount = 2;
    private Context context;

    public ViewPagerAdapter(FragmentManager manager, Context context) {
        super(manager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TimeRecorder();
            case 1:
                return new Memo();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.timer_tab_title);
            case 1:
                return context.getResources().getString(R.string.memo_tab_title);
            default:
                return null;
        }
    }
}