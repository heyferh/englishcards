package com.feku.englishcards.fragment;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.feku.englishcards.App;
import com.feku.englishcards.R;
import com.feku.englishcards.dao.CardDao;
import com.feku.englishcards.dictionary.CardProducer;
import com.feku.englishcards.entity.Card;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardFlipFragment extends Fragment {
    static TextToSpeech textToSpeech;
    private static CardProducer cardProducer = App.getCardProducer();
    private static Long dictionaryId = 0L;

    public CardFlipFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card_flip, container, false);
        Long dictID = (long) getArguments().getInt("ID");
        textToSpeech = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                //nop
            }
        });
        textToSpeech.setLanguage(Locale.US);
        dictionaryId = dictID;
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.cardContainer, new CardFragment())
                    .commit();
        }

        view.findViewById(R.id.cardContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }
        });
        return view;
    }

    private void flipCard() {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                .replace(R.id.cardContainer, new CardFlipFragment.CardFragment())
                .commit();
    }


    public static class CardFragment extends Fragment {
        private CardDao cardDao = App.getCardDao();
        static Card card;

        public CardFragment() {

        }

        public void addToFavourites(View view) {
            CheckBox favourite = (CheckBox) view.findViewById(R.id.favourite);
            Card card = CardFragment.card;
            if (favourite.isChecked()) {
                cardDao.load(card.getId()).setFavourite(true);
            } else {
                cardDao.load(card.getId()).setFavourite(false);
            }
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public void sound(View view) {
            textToSpeech.speak(CardFragment.card.getEnglishWord(), TextToSpeech.QUEUE_FLUSH, null);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_card, container, false);
            card = cardProducer.getAnotherCard(dictionaryId);
            CheckBox favourite = (CheckBox) view.findViewById(R.id.favourite);
            if (card.getFavourite()) {
                favourite.setChecked(true);
            } else {
                favourite.setChecked(false);
            }
            ((TextView) view.findViewById(R.id.cardWord)).setText(card.getEnglishWord());
            ((TextView) view.findViewById(R.id.cardTranslation)).setText(card.getRussianWord());
            view.findViewById(R.id.favourite).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToFavourites(v);
                }
            });
            view.findViewById(R.id.speech).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sound(v);
                }
            });
            return view;
        }
    }
}
