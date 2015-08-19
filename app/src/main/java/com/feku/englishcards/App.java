package com.feku.englishcards;

import android.database.sqlite.SQLiteDatabase;

import com.feku.englishcards.dao.CardDao;
import com.feku.englishcards.dao.DictionaryDao;
import com.feku.englishcards.dao.util.DaoMaster;
import com.feku.englishcards.dao.util.DaoSession;
import com.feku.englishcards.dictionary.CardProducer;

/**
 * Created by feku on 8/14/2015.
 */
public class App extends android.app.Application {
    private static App appInstance;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private DictionaryDao dictionaryDao;
    private CardProducer cardProducer;
    private CardDao cardDao;

    public static App getInstance() {
        return appInstance;
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
        cardProducer = new CardProducer(dictionaryDao, cardDao);
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
