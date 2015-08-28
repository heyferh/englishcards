package com.feku.englishcards.activity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.feku.englishcards.App;
import com.feku.englishcards.R;
import com.feku.englishcards.dao.CardDao;
import com.feku.englishcards.dictionary.CardProducer;
import com.feku.englishcards.entity.Card;
import com.feku.englishcards.fragment.CardFragment;

import org.joda.time.LocalDate;

/**
 * Created by feku on 8/26/2015.
 */
public class LeitnerModeActivity extends ActivityWithDrawer implements CardFragment.onCardActionListener {
    private static int CARD_LEVEL = 1;
    private CardDao cardDao = App.getCardDao();
    private CardProducer cardProducer = App.getCardProducer();
    MenuItem levelMenu;

    @Override
    protected void initOnCreate() {
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
        Card card = cardProducer.getAnotherLeitnerCard(1);
        CardFragment newCard = CardFragment.newInstance(card);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, newCard)
                .commit();
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
        Card card = cardProducer.getAnotherLeitnerCard(1);
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
        Card card = cardProducer.getAnotherLeitnerCard(CARD_LEVEL);
        CardFragment cardFragment = CardFragment.newInstance(card);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, cardFragment)
                .commit();
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
