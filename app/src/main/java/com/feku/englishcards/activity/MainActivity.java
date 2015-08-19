package com.feku.englishcards.activity;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.feku.englishcards.App;
import com.feku.englishcards.R;
import com.feku.englishcards.dao.CardDao;
import com.feku.englishcards.dao.DictionaryDao;
import com.feku.englishcards.entity.Card;
import com.feku.englishcards.entity.Dictionary;
import com.feku.englishcards.fragment.DictSelect;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends ActionBarActivity {
    private DictionaryDao dictionaryDao = App.getDictionaryDao();
    private CardDao cardDao = App.getCardDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getPreferences(MODE_PRIVATE).getBoolean("DB_EXIST", false)) {
            loadDictionaries();
        }
        setContentView(R.layout.activity_main);

        new Drawer()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_browse).withIcon(FontAwesome.Icon.faw_archive).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_view_starred).withIcon(FontAwesome.Icon.faw_star).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_leitner_mode).withIcon(FontAwesome.Icon.faw_line_chart).withIdentifier(3),
                        new SectionDrawerItem().withName(R.string.drawer_item_settings),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_secondary).withIcon(FontAwesome.Icon.faw_cog))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.activityContainer, new DictSelect())
                                .commit();
                    }
                })
                .build();
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
