package com.feku.englishcards.activity;

import com.feku.englishcards.App;
import com.feku.englishcards.R;
import com.feku.englishcards.dictionary.CardProducer;
import com.feku.englishcards.entity.Card;
import com.feku.englishcards.fragment.CardFragment;


public class RegularCardActivity extends ActivityWithDrawer implements CardFragment.onCardActionListener {
    private CardProducer cardProducer = App.getCardProducer();

    @Override
    protected void initOnCreate() {
        drawer = drawer.withSelectedItem(0);
        drawer.build();

        Card card = cardProducer.getAnotherCard((long) getIntent().getExtras().getInt("DICTIONARY_ID"));
        CardFragment newCard = CardFragment.newInstance(card);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, newCard)
                .commit();
    }

    @Override
    public void onCardTapped() {
        Card card = cardProducer.getAnotherCard((long) getIntent().getExtras().getInt("DICTIONARY_ID"));
        CardFragment newCard = CardFragment.newInstance(card);
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                .replace(R.id.container, newCard)
                .commit();
    }

    @Override
    protected int getLayout() {
        return R.layout.default_layout;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
