package com.feku.englishcards.fragment;

import android.app.Activity;
import android.app.Fragment;
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
import com.feku.englishcards.entity.Card;

public class CardFragment extends Fragment {
    private static final String ARG_1 = "CURRENT_CARD";
    private TextToSpeech textToSpeech = App.getTextToSpeech();
    private CardDao cardDao = App.getCardDao();
    private Card currentCard;

    private onCardActionListener mListener;

    public static CardFragment newInstance(Card card) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_1, card);
        fragment.setArguments(args);
        return fragment;
    }

    public CardFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentCard = (Card) getArguments().getSerializable(ARG_1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_layout, container, false);

        ((TextView) view.findViewById(R.id.cardWord)).setText(currentCard.getEnglishWord());
        ((TextView) view.findViewById(R.id.cardTranslation)).setText(currentCard.getRussianWord());

        CheckBox favourite = (CheckBox) view.findViewById(R.id.favourite);
        if (currentCard.getFavourite()) {
            favourite.setChecked(true);
        } else {
            favourite.setChecked(false);
        }

        view.findViewById(R.id.favourite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCard.setFavourite(!currentCard.getFavourite());
                cardDao.update(currentCard);
            }
        });
        view.findViewById(R.id.speech).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak(currentCard.getEnglishWord(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        view.findViewById(R.id.frontCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCardTapped();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (onCardActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onCardActionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface onCardActionListener {

        void onCardTapped();
    }

}
