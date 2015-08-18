package com.feku.englishcards.entity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table "CARD".
 */
public class Card {

    private Long id;
    /**
     * Not-null value.
     */
    private String englishWord;
    /**
     * Not-null value.
     */
    private String russianWord;
    private Boolean favourite;
    private long dictionaryId;

    public Card() {
    }

    public Card(Long id) {
        this.id = id;
    }

    public Card(Long id, String englishWord, String russianWord, Boolean favourite, long dictionaryId) {
        this.id = id;
        this.englishWord = englishWord;
        this.russianWord = russianWord;
        this.favourite = favourite;
        this.dictionaryId = dictionaryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Not-null value.
     */
    public String getEnglishWord() {
        return englishWord;
    }

    /**
     * Not-null value; ensure this value is available before it is saved to the database.
     */
    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    /**
     * Not-null value.
     */
    public String getRussianWord() {
        return russianWord;
    }

    /**
     * Not-null value; ensure this value is available before it is saved to the database.
     */
    public void setRussianWord(String russianWord) {
        this.russianWord = russianWord;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

}