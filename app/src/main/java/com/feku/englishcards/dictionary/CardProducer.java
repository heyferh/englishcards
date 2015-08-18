package com.feku.englishcards.dictionary;

import com.feku.englishcards.dao.CardDao;
import com.feku.englishcards.dao.DictionaryDao;
import com.feku.englishcards.entity.Card;

import java.util.Stack;

/**
 * Created by feku on 8/14/2015.
 */
public class CardProducer {
    private Long dictionaryId = -1L;
    private Stack<Card> cardStack = new Stack<>();
    private CardDao cardDao;
    private DictionaryDao dictionaryDao;

    //TODO: works not as expected, try to add a deque instead
    public Card getAnotherCard(Long dictionaryId) {
        if (!this.dictionaryId.equals(dictionaryId)) {
            loadDictionary(dictionaryId);
            this.dictionaryId = dictionaryId;
        }
        //
        return cardStack.pop();
    }

    public CardProducer(DictionaryDao dictionaryDao, CardDao cardDao) {
        this.dictionaryDao = dictionaryDao;
        this.cardDao = cardDao;
    }


    private void loadDictionary(Long dictionaryId) {
        for (Card card : dictionaryDao.load(dictionaryId).getCardList()) {
            cardStack.push(card);
        }
    }
}
