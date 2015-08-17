package com.feku.englishcards;

import com.feku.englishcards.dictionary.CardProducer;

/**
 * Created by feku on 8/14/2015.
 */
public class App extends android.app.Application {
    private static App appInstance;
    private CardProducer cardProducer;

    public static App getInstance() {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        cardProducer = new CardProducer(getApplicationContext());
    }

    public static CardProducer getCardProducer() {
        return getInstance().cardProducer;
    }
}
