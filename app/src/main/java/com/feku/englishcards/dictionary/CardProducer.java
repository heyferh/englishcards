package com.feku.englishcards.dictionary;

import com.feku.englishcards.dao.CardDao;
import com.feku.englishcards.dao.DictionaryDao;
import com.feku.englishcards.entity.Card;

import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;
import java.util.Stack;

/**
 * Created by feku on 8/14/2015.
 */
public class CardProducer {
    private Long dictionaryId = -1L;
    private int cardLevel = 0;
    private Stack<Card> cardStack = new Stack<>();
    private Stack<Card> favouriteCardStack = new Stack<>();
    private CardDao cardDao;
    private DictionaryDao dictionaryDao;
    private Stack<Card> leitnerStack = new Stack<>();

    public Card getAnotherCard(Long dictionaryId) {
        if (!this.dictionaryId.equals(dictionaryId)) {
            loadDictionary(dictionaryId);
            this.dictionaryId = dictionaryId;
        }
        return cardStack.pop();
    }

    public Card getAnotherFavouriteCard() {
        if (favouriteCardStack.isEmpty()) {
            List<Card> list = cardDao.queryBuilder()
                    .where(CardDao.Properties.Favourite.eq(Boolean.TRUE))
                    .list();
            for (Card card : list) {
                favouriteCardStack.push(card);
            }
        }
        return favouriteCardStack.pop();
    }

    public CardProducer(DictionaryDao dictionaryDao, CardDao cardDao) {
        this.dictionaryDao = dictionaryDao;
        this.cardDao = cardDao;
    }


    private void loadDictionary(Long dictionaryId) {
        cardStack.clear();
        for (Card card : dictionaryDao.load(dictionaryId).getCardList()) {
            cardStack.push(card);
        }
    }

    public Card getAnotherLeitnerCard(int cardLevel) {
        if (this.cardLevel != cardLevel) {
            loadAnotherLevel(cardLevel);
            this.cardLevel = cardLevel;
        }
        if (leitnerStack.isEmpty()) {
            loadAnotherLevel(cardLevel);
        }
        return leitnerStack.pop();
    }

    private void loadAnotherLevel(int cardLevel) {
        Date date;
        switch (cardLevel) {
            case 3:
                date = LocalDate.now().minusDays(5).toDate();
                break;
            case 2:
                date = LocalDate.now().minusDays(3).toDate();
                break;
            default:
                date = LocalDate.now().toDate();
        }
        leitnerStack.clear();
        List<Card> list = cardDao.queryBuilder()
                .where(CardDao.Properties.CardLevel.eq(cardLevel))
                .where(CardDao.Properties.Updated.le(date))
                .list();
        for (Card card : list) {
            leitnerStack.push(card);
        }
    }
}
