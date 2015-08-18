package com.feku.englishcards.dictionary;

import com.feku.englishcards.dao.DictionaryDao;
import com.feku.englishcards.entity.Card;

import java.util.Stack;

/**
 * Created by feku on 8/14/2015.
 */
public class CardProducer {
    private DictionaryDao dictionaryDao;
    private Long dictionaryId = -1L;
    private Stack<Card> cardStack = new Stack<>();

    //TODO: add the empty stack case behaviour
    public Card getAnotherCard(Long dictionaryId) {
        if (this.dictionaryId != dictionaryId) {
            cardStack.clear();
            loadDictionary(dictionaryId);
            this.dictionaryId = dictionaryId;
        }
        return cardStack.pop();
    }

    public CardProducer(DictionaryDao dictionaryDao) {
        this.dictionaryDao = dictionaryDao;
    }

    private void loadDictionary(Long dictionaryId) {
        for (Card card : dictionaryDao.load(dictionaryId).getCardList()) {
            cardStack.push(card);
        }
    }
}
