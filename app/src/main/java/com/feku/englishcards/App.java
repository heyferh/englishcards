package com.feku.englishcards;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;

import com.feku.englishcards.dao.CardDao;
import com.feku.englishcards.dao.DictionaryDao;
import com.feku.englishcards.dao.util.DaoMaster;
import com.feku.englishcards.dao.util.DaoSession;
import com.feku.englishcards.dao.util.DataBaseLoader;
import com.feku.englishcards.dictionary.CardProducer;
import com.feku.englishcards.receiver.LeitnerNotification;

import org.joda.time.LocalDateTime;

import java.util.Locale;

/**
 * Created by feku on 8/14/2015.
 */
public class App extends Application {
    private static App appInstance;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private CardProducer cardProducer;
    private DictionaryDao dictionaryDao;
    private CardDao cardDao;
    private DataBaseLoader dataBaseLoader;
    private TextToSpeech textToSpeech;

    public static App getInstance() {
        return appInstance;
    }

    public static TextToSpeech getTextToSpeech() {
        return getInstance().textToSpeech;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "db-test", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        dictionaryDao = daoSession.getDictionaryDao();
        cardDao = daoSession.getCardDao();
        dataBaseLoader = new DataBaseLoader(getApplicationContext());
        cardProducer = new CardProducer(dictionaryDao, cardDao);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });
        textToSpeech.setLanguage(Locale.US);
        if (!getSharedPreferences("english_cards", MODE_PRIVATE).getBoolean("DB_EXIST", false)) {
            DataBaseLoader.loadDictionaries();
        }
        if (!getSharedPreferences("english_cards", MODE_PRIVATE).getBoolean("NOTIFICATIONS_SET_ALARM", false)) {
            setUpNotifications();
        }
    }

    private void setUpNotifications() {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, LeitnerNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        am.cancel(pendingIntent);
        LocalDateTime dateTime = LocalDateTime.now()
                .withHourOfDay(18)
                .withMinuteOfHour(0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, dateTime.toDate().getTime(), AlarmManager.INTERVAL_DAY, pendingIntent);
        SharedPreferences preferences = getSharedPreferences("english_cards", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("NOTIFICATIONS_SET_ALARM", true).commit();
    }

    public static CardProducer getCardProducer() {
        return getInstance().cardProducer;
    }

    public static CardDao getCardDao() {
        return getInstance().cardDao;
    }

    public static DictionaryDao getDictionaryDao() {
        return getInstance().dictionaryDao;
    }
}
