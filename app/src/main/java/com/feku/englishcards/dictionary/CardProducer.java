package com.feku.englishcards.dictionary;

import android.content.Context;
import android.content.res.Resources;

import com.feku.englishcards.R;
import com.feku.englishcards.entity.Card;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by feku on 8/14/2015.
 */
public class CardProducer {
    private Context context;
    private int dictionaryId = 0;
    private Stack<Card> cardStack = new Stack<>();

    public CardProducer(Context context) {
        this.context = context;
        loadDictionary(0);
    }

    //TODO: add the empty stack case behaviour
    public Card getAnotherCard(int dictionaryId) {
        if (this.dictionaryId != dictionaryId) {
            cardStack.clear();
            loadDictionary(dictionaryId);
            this.dictionaryId = dictionaryId;
        }
        return cardStack.pop();
    }

    private void loadDictionary(int dictionaryId) {
        Resources resources = context.getResources();
        String text = resources.getStringArray(R.array.dictionaries)[dictionaryId];
        String fileName = text.substring(text.indexOf("|") + 1);
        InputStream inputStream = resources.openRawResource(resources.getIdentifier(fileName, "raw", context.getPackageName()));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            NodeList nodeList = document.getElementsByTagName("card");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                cardStack.push(new Card(node.getFirstChild().getTextContent(), node.getLastChild().getTextContent()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
