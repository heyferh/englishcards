package com.feku.englishcards.dao.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import com.feku.englishcards.App;
import com.feku.englishcards.dao.CardDao;
import com.feku.englishcards.dao.DictionaryDao;
import com.feku.englishcards.entity.Card;
import com.feku.englishcards.entity.Dictionary;

import org.joda.time.LocalDate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by feku on 8/20/2015.
 */
public class DataBaseLoader {
    private static Context context;
    private static DictionaryDao dictionaryDao = App.getDictionaryDao();
    private static CardDao cardDao = App.getCardDao();

    public DataBaseLoader(Context applicationContext) {
        context = applicationContext;
    }

    public static void loadDictionaries() {
        long start = System.currentTimeMillis();
        Resources resources = context.getResources();
        InputStream inputStream = resources.openRawResource(resources.getIdentifier("full", "raw", context.getPackageName()));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            NodeList dictionaries = document.getElementsByTagName("dictionary");
            Set<Dictionary> dictionarySet = new HashSet<>();
            Set<Card> cardSet = new HashSet<>();
            for (int i = 0; i < dictionaries.getLength(); i++) {
                NodeList cards = dictionaries.item(i).getChildNodes();
                Long dictionaryId = Long.valueOf(((Element) dictionaries.item(i)).getAttribute("id"));
                String title = ((Element) dictionaries.item(i)).getAttribute("title");
                dictionarySet.add(new Dictionary(dictionaryId, title));
                for (int j = 0; j < cards.getLength(); j++) {
                    String english = cards.item(j).getFirstChild().getTextContent();
                    String russian = cards.item(j).getLastChild().getTextContent();
                    Long cardId = Long.valueOf(((Element) cards.item(j)).getAttribute("id"));
                    Card card = new Card(cardId, english, russian, false, 1, LocalDate.now().toDate(), dictionaryId);
                    cardSet.add(card);
                }
            }
            cardDao.insertInTx(cardSet);
            dictionaryDao.insertInTx(dictionarySet);
            Log.i("English Card: ", "Total time consumed in millis: " + String.valueOf((System.currentTimeMillis() - start)));
            Log.i("English Card: ", "Total dictionaries: " + dictionaryDao.loadAll().size());
            Log.i("English Card: ", "Total cards: " + cardDao.loadAll().size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences preferences = context.getSharedPreferences("english_cards", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("DB_EXIST", true);
        editor.commit();
    }
}
