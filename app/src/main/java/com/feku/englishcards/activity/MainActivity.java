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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends Activity {
    private DictionaryDao dictionaryDao = App.getDictionaryDao();
    private CardDao cardDao = App.getCardDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!getPreferences(MODE_PRIVATE).getBoolean("DB_EXIST", false)) {
            loadDictionaries();
        }

        startActivity(new Intent(this, DictChoiceActivity.class));
    }

    private void loadDictionaries() {
        Resources resources = getResources();
        String[] dictionaries = resources.getStringArray(R.array.dictionaries);
        formatDictNames(dictionaries);
        for (String fileName : dictionaries) {
            InputStream inputStream = resources.openRawResource(resources.getIdentifier(fileName, "raw", getPackageName()));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(inputStream);
                NodeList nodeList = document.getElementsByTagName("card");
                Long dictId = Long.valueOf(((Element) document.getFirstChild()).getAttribute("id"));
                String title = ((Element) document.getFirstChild()).getAttribute("title");
                dictionaryDao.insert(new Dictionary(dictId, title));
                for (int i = 0; i < nodeList.getLength(); i++) {

                    Node node = nodeList.item(i);
                    Long cardId = Long.valueOf(((Element) node).getAttribute("id"));
                    Card card = new Card(cardId, node.getFirstChild().getTextContent(), node.getLastChild().getTextContent(), dictId);
                    cardDao.insert(card);
                    Log.i("English Cards", "Inserted: " + dictId + " " + card);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("DB_EXIST", true);
        editor.commit();
    }

    private static void formatDictNames(String[] dictNames) {
        for (int i = 0; i < dictNames.length; i++) {
            dictNames[i] = dictNames[i].substring(dictNames[i].indexOf("|") + 1);
        }
    }
}
