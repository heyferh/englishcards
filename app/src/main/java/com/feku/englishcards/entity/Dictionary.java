package com.feku.englishcards.entity;

import java.io.Serializable;
import java.util.List;

import com.feku.englishcards.dao.CardDao;
import com.feku.englishcards.dao.util.DaoSession;
import com.feku.englishcards.dao.DictionaryDao;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "DICTIONARY".
 */
public class Dictionary implements Serializable{

    private Long id;
    /** Not-null value. */
    private String title;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient DictionaryDao myDao;

    private List<Card> cardList;

    public Dictionary() {
    }

    public Dictionary(Long id) {
        this.id = id;
    }

    public Dictionary(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDictionaryDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getTitle() {
        return title;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Card> getCardList() {
        if (cardList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CardDao targetDao = daoSession.getCardDao();
            List<Card> cardListNew = targetDao._queryDictionary_CardList(id);
            synchronized (this) {
                if(cardList == null) {
                    cardList = cardListNew;
                }
            }
        }
        return cardList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetCardList() {
        cardList = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
