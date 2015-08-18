package com.feku.englishcards.dao.util;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by feku on 8/18/2015.
 */
public class ExDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1000, "com.feku.englishcards.dao");

        addDictionary(schema);

        new DaoGenerator().generateAll(schema, "app/src/main/java");
    }

    private static void addDictionary(Schema schema) {
        Entity dictionary = schema.addEntity("Dictionary");
        dictionary.addIdProperty();
        dictionary.addStringProperty("title").notNull();

        Entity card = schema.addEntity("Card");
        card.addIdProperty().getProperty();
        card.addStringProperty("englishWord").notNull();
        card.addStringProperty("russianWord").notNull();
        Property dictionaryId = card.addLongProperty("dictionaryId").notNull().getProperty();

        dictionary.addToMany(card, dictionaryId);

    }

}