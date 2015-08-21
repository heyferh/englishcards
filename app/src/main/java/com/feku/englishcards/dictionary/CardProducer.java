package com.feku.englishcards.dictionary;

import com.feku.englishcards.dao.CardDao;
import com.feku.englishcards.dao.DictionaryDao;
import com.feku.englishcards.entity.Card;

import java.util.List;
import java.util.Stack;

/**
 * Created by feku on 8/14/2015.
 */
public class CardProducer {
    private Long dictionaryId = -1L;
    private Stack<Card> cardStack = new Stack<>();
    private Stack<Card> favouriteCardStack = new Stack<>();
    private CardDao cardDao;
    private DictionaryDao dictionaryDao;

    //TODO: add the empty stack case
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

    //TODO: Reconsider purpose of the CardProducer
    public Card getAnotherLeitnerCard(int cardLevel) {
        return null;
    }
}
