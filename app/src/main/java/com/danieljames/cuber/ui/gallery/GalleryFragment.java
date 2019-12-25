package com.danieljames.cuber.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.danieljames.cuber.R;
import com.danieljames.cuber.letters.LettersPager;
import com.google.android.material.tabs.TabLayout;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.letters_tabs);
        LettersPager adapter = new LettersPager(getFragmentManager(), getActivity());
        ViewPager viewPager = (ViewPager) root.findViewById(R.id.letters_viewpager);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return root;
    }
}