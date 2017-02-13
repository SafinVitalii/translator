package com.safin.translator; /**
 * Created by vitalii.safin on 2/2/2017.
 */

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Translator {
    private MyHashMap<String, String> dictionary = new MyHashMap<String, String>();
    private IOProcessor ioProcessor = new IOProcessor(dictionary);
    private String baseLanguage = "English";
    private String translatingLanguage = "Ukrainian";

    Translator() {
        Translate.setClientId("13a437a0-fabf-4b62-9fde-c9c6005a6bdc");
        Translate.setClientSecret("cLQx18TKFvLlxh0OrpLfPBylBiB65G5rTY7x2GDysUg");
    }

    String getBaseLanguage() {
        return baseLanguage;
    }

    String getTranslatingLanguage() {
        return translatingLanguage;
    }

    void setBaseLanguage(String language) {
        baseLanguage = language;
    }

    void setTranslatingLanguage(String language) {
        translatingLanguage = language;
    }

    String beginTranslation(String basicText) throws Exception {
        try {
            String translatedText = ioProcessor.getFromOffline(basicText);
            if (!translatedText.equals("Not found in offline dictionary")) {
                return translatedText;
            }
            basicText = basicText.toLowerCase();
            try {
                translatedText = Translate.execute
                        (basicText, Language.valueOf(baseLanguage.toUpperCase()), Language.valueOf(translatingLanguage.toUpperCase()));
                ioProcessor.addToOfflineData(basicText);
            } catch (IOException ex) {
                System.out.println("IOException!" + ex.toString());
            } catch (Exception ex) {
                translatedText = ioProcessor.getFromOffline(basicText);
            }
            System.out.println(translatedText);
            System.out.println();
            return translatedText;
        } catch (Exception ex) {
            System.out.println("Exception during translation!" + Arrays.toString(ex.getStackTrace()));
        }
        return "";
    }
}
