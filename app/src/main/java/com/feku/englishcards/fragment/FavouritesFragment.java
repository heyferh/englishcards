package com.feku.englishcards.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feku.englishcards.R;

/**
 * Created by feku on 8/20/2015.
 */
public class FavouritesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourites_layout, container, false);
        CardFragment cardFragment = new CardFragment();
        cardFragment.setArguments(getArguments());
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.favouriteCardsContainer, cardFragment)
                    .commit();
        }
        view.findViewById(R.id.favouriteCardsContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }
        });
        return view;
    }

    private void flipCard() {
        CardFragment cardFragment = new CardFragment();
        cardFragment.setArguments(getArguments());
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                .replace(R.id.favouriteCardsContainer, cardFragment)
                .commit();
    }
}