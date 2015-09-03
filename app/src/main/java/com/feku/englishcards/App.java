package com.feku.englishcards;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;

import com.feku.englishcards.dao.CardDao;
import com.feku.englishcards.dao.DictionaryDao;
import com.feku.englishcards.dao.util.DaoMaster;
import com.feku.englishcards.dao.util.DaoSession;
import com.feku.englishcards.dao.util.DataBaseLoader;
import com.feku.englishcards.dictionary.CardProducer;

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
