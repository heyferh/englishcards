package com.feku.englishcards.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feku.englishcards.R;
import com.feku.englishcards.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeitnersModeFragment extends Fragment {


    public LeitnersModeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leitners_mode, container, false);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Leitner system");
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.leitner_levels, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
