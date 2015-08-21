package com.feku.englishcards.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.feku.englishcards.R;
import com.feku.englishcards.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeitnersModeFragment extends Fragment {
    private static int CARD_LEVEL = 1;

    public LeitnersModeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.card_flip_layout, container, false);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Leitner system");
        view.findViewById(R.id.cardContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }
        });
        flipCard();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.leitner_levels, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CardFragment cardFragment = new CardFragment();
        switch (item.getTitle().toString()) {
            case "Level 1":
                CARD_LEVEL = 1;
                break;
            case "Level 2":
                CARD_LEVEL = 2;
                break;
            case "Level 3":
                CARD_LEVEL = 3;
                break;
            default:
                return false;
        }
        getArguments().putInt("CARD_LEVEL", CARD_LEVEL);
        cardFragment.setArguments(getArguments());
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.cardContainer, cardFragment)
                .commit();
        return super.onOptionsItemSelected(item);
    }

    private void flipCard() {
        CardFragment cardFragment = new CardFragment();
        cardFragment.setArguments(getArguments());
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                .replace(R.id.cardContainer, cardFragment)
                .commit();
    }
}
