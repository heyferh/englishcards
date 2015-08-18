package com.feku.englishcards.dao.util;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.feku.englishcards.entity.Dictionary;
import com.feku.englishcards.entity.Card;

import com.feku.englishcards.dao.DictionaryDao;
import com.feku.englishcards.dao.CardDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig dictionaryDaoConfig;
    private final DaoConfig cardDaoConfig;

    private final DictionaryDao dictionaryDao;
    private final CardDao cardDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        dictionaryDaoConfig = daoConfigMap.get(DictionaryDao.class).clone();
        dictionaryDaoConfig.initIdentityScope(type);

        cardDaoConfig = daoConfigMap.get(CardDao.class).clone();
        cardDaoConfig.initIdentityScope(type);

        dictionaryDao = new DictionaryDao(dictionaryDaoConfig, this);
        cardDao = new CardDao(cardDaoConfig, this);

        registerDao(Dictionary.class, dictionaryDao);
        registerDao(Card.class, cardDao);
    }
    
    public void clear() {
        dictionaryDaoConfig.getIdentityScope().clear();
        cardDaoConfig.getIdentityScope().clear();
    }

    public DictionaryDao getDictionaryDao() {
        return dictionaryDao;
    }

    public CardDao getCardDao() {
        return cardDao;
    }

}
