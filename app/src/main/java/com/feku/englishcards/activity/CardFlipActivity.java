/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.feku.englishcards.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
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

public class CardFlipActivity extends Activity {

    private CardDao cardDao = App.getCardDao();
    private static CardProducer cardProducer = App.getCardProducer();
    private static Long dictionaryId = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_flip);
        Long dictID = (long) getIntent().getExtras().getInt("ID");
        dictionaryId = dictID;
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new CardFragment())
                    .commit();
        }
        findViewById(R.id.container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCard();
            }
        });

    }

    private void flipCard() {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                .replace(R.id.container, new CardFragment())
                .commit();
    }

    public void addToFavourites(View view) {
        CheckBox favourite = (CheckBox) findViewById(R.id.favourite);
        Card card = CardFragment.card;
        if (favourite.isChecked()) {
            cardDao.load(card.getId()).setFavourite(true);
        } else {
            cardDao.load(card.getId()).setFavourite(false);
        }
    }


    public static class CardFragment extends Fragment {
        static Card card;

        public CardFragment() {
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
            return view;
        }
    }
}