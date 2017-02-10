package com.safin.translator;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by vitalii.safin on 2/2/2017.
 */
public class IOProcessor {

    MyHashMap<String, String> dictionary;
    DatabaseConnection database;

    IOProcessor(MyHashMap<String, String> dictionary) {
        this.dictionary = dictionary;
        database = new DatabaseConnection(dictionary);
    }

    String getFromOffline(String text) {
        String result = null;
        if (database.existsInDatabase(text)) {
            result = dictionary.get(text);
        }
        return (result != null) ? result : "Not found in offline dictionary";
    }

    private String[] splitText(String text) {
        String cleanedText = text.replaceAll("[^a-zA-Z\\s]", "").replaceAll("\\s+", " ");
        return cleanedText.split(" ");
    }

    boolean addToOfflineData(String text) {
        try {
            String[] splittedText = splitText(text);
            for(String str : splittedText) {
                String translation = "";
                try {
                    translation = Translate.execute(str, Language.ENGLISH, Language.UKRAINIAN);
                } catch (Exception ex) {
                    System.out.println("Getting from offline!");
                    if (dictionary.containsKey(str)) {
                        translation = dictionary.get(str);
                    }
                } finally {
                    if (translation.contains("TranslateApiException")) {
                        return false;
                    }
                    if ((dictionary.put(str, translation)) == null) {
                        database.insertData(str, translation);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Unknown exception during updating offline data!" + ex.toString());
            return false;
        }
        return true;
    }
}
