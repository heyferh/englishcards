package com.feku.englishcards.activity;

import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.feku.englishcards.App;
import com.feku.englishcards.R;
import com.feku.englishcards.dao.DictionaryDao;
import com.feku.englishcards.dictionary.CardProducer;
import com.feku.englishcards.entity.Card;
import com.feku.englishcards.fragment.CardFragment;


public class RegularCardActivity extends ActivityWithDrawer implements CardFragment.onCardActionListener {
    private CardProducer cardProducer = App.getCardProducer();
    private DictionaryDao dictionaryDao = App.getDictionaryDao();

    @Override
    protected void initOnCreate() {
        long dictionaryId = (long) getIntent().getExtras().getInt("DICTIONARY_ID");
        String dictionaryTitle = dictionaryDao.load(dictionaryId).getTitle();
        getSupportActionBar().setTitle(dictionaryTitle);
        drawer = drawer
                .withSelectedItem(0)
                .withActionBarDrawerToggle(false);
        drawer.build();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        onCardTapped();
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
