package com.feku.englishcards.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.feku.englishcards.App;
import com.feku.englishcards.dao.CardDao;

import org.joda.time.LocalDate;

/**
 * Created by feku on 9/4/2015.
 */
public class DailyCardsCounter extends AsyncTask<Activity, Void, Void> {
    CardDao cardDao = App.getCardDao();

    @Override
    protected Void doInBackground(Activity... params) {

        SharedPreferences preferences = params[0].getSharedPreferences("english_cards", Context.MODE_PRIVATE);
        String key = LocalDate.now().toString();
        long newCardsCount = cardDao.queryBuilder()
                .where(CardDao.Properties.Updated.eq(LocalDate.now().toDate()))
                .count();
        preferences.edit().putLong(key, newCardsCount).commit();
        return null;
    }
}
