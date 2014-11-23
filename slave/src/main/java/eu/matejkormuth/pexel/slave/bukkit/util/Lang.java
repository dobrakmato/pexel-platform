// @formatter:off
/*
 * Pexel Project - Minecraft minigame server platform. 
 * Copyright (C) 2014 Matej Kormuth <http://www.matejkormuth.eu>
 * 
 * This file is part of Pexel.
 * 
 * Pexel is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Pexel is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 */
// @formatter:on
package eu.matejkormuth.pexel.slave.bukkit.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used for translations. Taken from project STARVING.
 * 
 * @see Lang#getTranslation(String);
 * 
 */
public class Lang {
    /**
     * Default language
     */
    private static final String        DEFAULT_LANGUAGE = "en";
    /**
     * Map containing all translations in formate {lang}_{phrase} = translatedPhrase;
     */
    private static Map<String, String> translations     = Collections.synchronizedMap(new HashMap<String, String>());
    /**
     * Singleton instance.
     */
    private volatile static Lang       instance;
    
    /**
     * Konstruktor na singleton.
     */
    private Lang() {
        
    }
    
    /**
     * Loads the translations to memory.
     */
    public static void loadTranslations(final String dataFolder) {
        System.out.println("[Translations] Nacitavam preklady...");
        //Get the files with translations.
        File folder = new File(dataFolder + "/lang/");
        File[] listOfFiles = folder.listFiles();
        
        //For each file
        for (File file : listOfFiles) {
            //If its a lang file.
            if (file.isFile() && file.getName().endsWith("lang")) {
                if (file.getName().contains(".")) {
                    System.out.println("[Translations] Found translation "
                            + file.getName());
                    //Read the file.
                    String translationLang = file.getName().split("\\.")[0];
                    //Reader
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new FileReader(file));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            //Real line, split by = and put into translations map.
                            String[] translation = line.split("=");
                            translations.put(translationLang + "_" + translation[0],
                                    translation[1]);
                        }
                        //Close the reader.
                        reader.close();
                    } catch (FileNotFoundException e) {
                    } catch (IOException e) {
                    }
                }
            }
        }
    }
    
    /**
     * Returns translated phrase by specified phrase code and default language.
     * 
     * @param phrase
     *            code (user-specified)
     * @return translated phrase or phrase code if the translation was not found
     */
    public static String getTranslation(final String phrase) {
        synchronized (Lang.translations) {
            if (Lang.translations.containsKey(DEFAULT_LANGUAGE + "_" + phrase)) {
                return Lang.translations.get(DEFAULT_LANGUAGE + "_" + phrase);
            }
            else {
                return DEFAULT_LANGUAGE + "_" + phrase;
            }
        }
    }
    
    /**
     * Returns translated phrase by specified phrase code and default language.
     * 
     * @param phrase
     *            code (user-specified)
     * @param vars
     *            map of variabiles to replace (key is variabile, value is replacement
     * @return translated phrase or phrase code if the translation was not found
     */
    public static String getTranslation(final String phrase,
            final Map<String, String> vars) {
        String translation = "";
        synchronized (Lang.translations) {
            //Get translation
            translation = Lang.translations.get(DEFAULT_LANGUAGE + "_" + phrase);
        }
        
        //Replace variabiles
        for (String variabile : vars.keySet()) {
            translation = translation.replace("{" + variabile + "}", vars.get(variabile));
        }
        
        return translation;
    }
    
    /**
     * Returns translated phrase by specified phrase code and language.
     * 
     * @param phrase
     *            code (user-specified)
     * @param language
     *            language code (ISO 3166-1 Alpha-2 standard, two-letter code)
     * @return translated phrase or phrase code if the translation was not found
     */
    public static String getTranslation(final String phrase, final String language) {
        synchronized (Lang.translations) {
            if (Lang.translations.containsKey(language + "_" + phrase)) {
                return Lang.translations.get(language + "_" + phrase);
            }
            else {
                return language + "_" + phrase;
            }
        }
    }
    
    /**
     * Returns translated phrase by specified phrase code and language.
     * 
     * @param phrase
     *            code (user-specified)
     * @param language
     *            language code (ISO 3166-1 Alpha-2 standard, two-letter code)
     * @param vars
     *            map of variabiles to replace (key is variabile, value is replacement
     * @return translated phrase
     */
    public static String getTranslation(final String phrase, final String language,
            final Map<String, String> vars) {
        String translation = "";
        synchronized (Lang.translations) {
            //Get translation
            translation = Lang.translations.get(language + "_" + phrase);
        }
        
        //Replace variabiles
        for (String variabile : vars.keySet()) {
            translation = translation.replace("{" + variabile + "}", vars.get(variabile));
        }
        
        return translation;
    }
    
    /**
     * Returns country code which is used as language code by IP address.
     * 
     * @return language code (SO 3166-1 Alpha-2 standard, two-letter code)
     */
    public static String getLang() {
        return "en";
    }
    
    /**
     * Returns the instance of Lang.
     * 
     * @return
     */
    public static Lang getInstance() {
        if (Lang.instance != null)
            return Lang.instance;
        else
            return Lang.instance = new Lang();
    }
}
