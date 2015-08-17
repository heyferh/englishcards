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
import android.widget.TextView;
import android.widget.Toast;

import com.feku.englishcards.App;
import com.feku.englishcards.R;
import com.feku.englishcards.dictionary.CardProducer;
import com.feku.englishcards.entity.Card;

public class CardFlipActivity extends Activity {

    private static int dictionaryId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_flip);
        int dictID = getIntent().getExtras().getInt("ID");
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

    public void onToggleStar(View view) {
        Toast.makeText(this, "Toggled!", Toast.LENGTH_LONG).show();
    }

    public static class CardFragment extends Fragment {
        private CardProducer cardProducer = App.getCardProducer();

        public CardFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_card, container, false);
            Card card = cardProducer.getAnotherCard(dictionaryId);
            ((TextView) view.findViewById(R.id.cardWord)).setText(card.getEnglishWord());
            ((TextView) view.findViewById(R.id.cardTranslation)).setText(card.getRussianWord());
            return view;
        }
    }
}