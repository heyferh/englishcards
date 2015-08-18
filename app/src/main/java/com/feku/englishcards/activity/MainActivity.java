package com.feku.englishcards.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.feku.englishcards.App;
import com.feku.englishcards.R;
import com.feku.englishcards.dao.CardDao;
import com.feku.englishcards.dao.DictionaryDao;
import com.feku.englishcards.entity.Card;
import com.feku.englishcards.entity.Dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends Activity {
    private DictionaryDao dictionaryDao = App.getDictionaryDao();
    private CardDao cardDao = App.getCardDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPreferences(MODE_PRIVATE).edit().remove("DB_EXIST").commit();
        if (!getPreferences(MODE_PRIVATE).getBoolean("DB_EXIST", false)) {
            loadDictionaries();
        }

        startActivity(new Intent(this, DictChoiceActivity.class));
    }

    private void loadDictionaries() {
        long start = System.currentTimeMillis();
        Resources resources = getResources();
        InputStream inputStream = resources.openRawResource(resources.getIdentifier("full", "raw", getPackageName()));
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
                    Card card = new Card(cardId, english, russian, false, dictionaryId);
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
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("DB_EXIST", true);
        editor.commit();
    }
}
