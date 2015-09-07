package com.feku.englishcards.activity;

import android.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.feku.englishcards.App;
import com.feku.englishcards.R;
import com.feku.englishcards.dictionary.CardProducer;
import com.feku.englishcards.entity.Card;
import com.feku.englishcards.exception.NoCardsException;
import com.feku.englishcards.fragment.CardFragment;
import com.feku.englishcards.fragment.EmptyCardFragment;

import java.util.Stack;

/**
 * Created by feku on 8/26/2015.
 */
public class FavouriteCardActivity extends ActivityWithDrawer implements CardFragment.onCardActionListener {
    private CardProducer cardProducer = App.getCardProducer();
    private Stack<Card> favouriteCardsStack = new Stack<>();

    @Override
    protected void initOnCreate() {
        getSupportActionBar().setTitle("Favourites");
        drawer = drawer
                .withSelectedItem(1)
                .withActionBarDrawerToggle(false);
        drawer.build();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        onCardTapped();
    }

    @Override
    public void onCardTapped() {
        Card card = null;
        Fragment fragment;
        try {
            card = getCard();
            fragment = CardFragment.newInstance(card);
        } catch (NoCardsException e) {
            fragment = EmptyCardFragment.newInstance("No favourite cards yet...");
        }
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                .replace(R.id.container, fragment)
                .commit();
    }

    private Card getCard() throws NoCardsException {
        if (favouriteCardsStack.isEmpty()) {
            favouriteCardsStack.addAll(cardProducer.getAnotherFavouriteCard());
        }
        if (favouriteCardsStack.isEmpty()) {
            throw new NoCardsException();
        }
        return favouriteCardsStack.pop();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
