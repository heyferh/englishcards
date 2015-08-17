package com.feku.englishcards.entity;

/**
 * Created by feku on 8/14/2015.
 */
public class Card {
    private final String englishWord;
    private final String russianWord;

    public Card(String englishWord, String russianWord) {
        this.englishWord = englishWord;
        this.russianWord = russianWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public String getRussianWord() {
        return russianWord;
    }
}
