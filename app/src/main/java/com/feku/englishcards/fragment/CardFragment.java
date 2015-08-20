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

/**
 * Created by feku on 8/20/2015.
 */
public class CardFragment extends Fragment {
    private static TextToSpeech textToSpeech = App.getTextToSpeech();
    private static long dictionaryId;
    private static CardDao cardDao = App.getCardDao();
    private static Card card;
    private static CardProducer cardProducer = App.getCardProducer();

    public CardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_layout, container, false);
        dictionaryId = getArguments().getLong("DICTIONARY_ID", 0);
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
}