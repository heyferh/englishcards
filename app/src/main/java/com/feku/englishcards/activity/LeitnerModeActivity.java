package com.feku.englishcards.activity;

import android.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.feku.englishcards.App;
import com.feku.englishcards.R;
import com.feku.englishcards.dao.CardDao;
import com.feku.englishcards.dictionary.CardProducer;
import com.feku.englishcards.entity.Card;
import com.feku.englishcards.exception.NoCardsException;
import com.feku.englishcards.fragment.CardFragment;
import com.feku.englishcards.fragment.EmptyCardFragment;

import org.joda.time.LocalDate;

/**
 * Created by feku on 8/26/2015.
 */
public class LeitnerModeActivity extends ActivityWithDrawer implements CardFragment.onCardActionListener {
    private static int CARD_LEVEL;
    private CardDao cardDao = App.getCardDao();
    private CardProducer cardProducer = App.getCardProducer();
    MenuItem levelMenu;

    @Override
    protected void initOnCreate() {
        CARD_LEVEL = 1;
        getSupportActionBar().setTitle("Leitner mode");
        drawer = drawer.withSelectedItem(2);
        drawer.build();
        findViewById(R.id.know).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseLevel();
            }
        });
        findViewById(R.id.dontKnow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetLevel();
            }
        });
        onCardTapped();
    }

    private void increaseLevel() {
        Card currentCard = CardFragment.getCurrentCard();
        currentCard.setCardLevel(currentCard.getCardLevel() + 1);
        currentCard.setUpdated(LocalDate.now().toDate());
        cardDao.update(currentCard);
        onCardTapped();
    }

    private void resetLevel() {
        Card currentCard = CardFragment.getCurrentCard();
        currentCard.setCardLevel(1);
        currentCard.setUpdated(LocalDate.now().toDate());
        cardDao.update(currentCard);
        onCardTapped();
    }

    @Override
    protected int getLayout() {
        return R.layout.leitner_layout;
    }

    @Override
    public void onCardTapped() {
        Fragment fragment;
        try {
            Card card = cardProducer.getAnotherLeitnerCard(CARD_LEVEL);
            fragment = CardFragment.newInstance(card);
        } catch (NoCardsException e) {
            fragment = EmptyCardFragment.newInstance("No cards available at level " + CARD_LEVEL + " yet...");
            findViewById(R.id.know).setVisibility(View.GONE);
            findViewById(R.id.dontKnow).setVisibility(View.GONE);
        }
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.leitner_levels, menu);
        levelMenu = menu.findItem(R.id.lv_picker);
        levelMenu.setTitle("Level 1");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().toString().contains(String.valueOf(CARD_LEVEL))) {
            return true;
        }
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
        levelMenu.setTitle(item.getTitle());
        findViewById(R.id.know).setVisibility(View.VISIBLE);
        findViewById(R.id.dontKnow).setVisibility(View.VISIBLE);
        onCardTapped();
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
