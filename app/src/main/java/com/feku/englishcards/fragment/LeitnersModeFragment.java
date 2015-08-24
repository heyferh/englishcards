package com.feku.englishcards.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.feku.englishcards.App;
import com.feku.englishcards.R;
import com.feku.englishcards.activity.MainActivity;
import com.feku.englishcards.dao.CardDao;

import org.joda.time.LocalDate;

import static com.feku.englishcards.fragment.CardFragment.card;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeitnersModeFragment extends Fragment {
    private static int CARD_LEVEL = 1;
    private CardDao cardDao = App.getCardDao();

    public LeitnersModeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.leitner_fragment_layout, container, false);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Leitner system");
        view.findViewById(R.id.leitnerContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }
        });
        view.findViewById(R.id.dontKnow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card.setCardLevel(1);
                card.setUpdated(LocalDate.now().toDate());
                cardDao.update(card);
                flipCard();
            }
        });
        view.findViewById(R.id.know).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!card.getCardLevel().equals(3)) {
                    card.setUpdated(LocalDate.now().toDate());
                    card.setCardLevel(card.getCardLevel() + 1);
                    cardDao.update(card);
                }
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
                .replace(R.id.leitnerContainer, cardFragment)
                .commit();
        return super.onOptionsItemSelected(item);
    }

    private void flipCard() {
        CardFragment cardFragment = new CardFragment();
        getArguments().putInt("CARD_LEVEL", CARD_LEVEL);
        cardFragment.setArguments(getArguments());
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                .replace(R.id.leitnerContainer, cardFragment)
                .commit();
    }
}
