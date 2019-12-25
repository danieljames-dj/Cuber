package com.danieljames.cuber.letters;

import android.content.Context;

import com.danieljames.cuber.R;
import com.danieljames.cuber.letters.learn.LettersLearnTab;
import com.danieljames.cuber.letters.list.LettersListTab;
import com.danieljames.cuber.letters.source.LettersSourceTab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class LettersPager extends FragmentPagerAdapter {

    Context context;
    LettersModel lettersModel;

    public LettersPager(FragmentManager manager, Context context) {
        super(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        LettersModel.lettersModel = new LettersModel(context);
        this.lettersModel = LettersModel.lettersModel;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LettersListTab lettersListTab = new LettersListTab();
                lettersListTab.lettersModel = this.lettersModel;
                return lettersListTab;
            case 1:
                LettersLearnTab lettersLearnTab = new LettersLearnTab();
                lettersLearnTab.lettersModel = this.lettersModel;
                return lettersLearnTab;
            case 2:
                LettersSourceTab lettersSourceTab = new LettersSourceTab();
                lettersSourceTab.lettersModel = this.lettersModel;
                return lettersSourceTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.tab_title_list);
            case 1:
                return context.getResources().getString(R.string.tab_title_learn);
            case 2:
                return context.getResources().getString(R.string.tab_title_source);
            default:
                return null;
        }
    }
}
