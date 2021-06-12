package com.meembusoft.ln.enumeration;

import android.content.Context;

import androidx.annotation.StringRes;

import com.meembusoft.ln.R;
import com.meembusoft.ln.base.BaseApp;
import com.meembusoft.ln.base.interfaces.BaseEnum;
import com.meembusoft.localemanager.languagesupport.LanguagesSupport;

public enum Language implements BaseEnum {

    ENGLISH(R.string.txt_language_english, LanguagesSupport.Language.ENGLISH),
    BENGALI(R.string.txt_language_bengali, LanguagesSupport.Language.BENGALI);

    private @StringRes
    int key = -1;
    private @LanguagesSupport.Language
    String language;

    Language(@StringRes int title, @LanguagesSupport.Language String languageCode) {
        key = title;
        language = languageCode;
    }

    @Override
    public String toString() {
        return BaseApp.getGlobalContext().getString(key);
    }

    @Override
    public String getLabel(Context context) {
        return context.getString(key);
    }

    @Override
    public int getKey() {
        return key;
    }

    public String getLanguageCode() {
        return language;
    }

    public static Language getLanguage(String languageCode) {
        for (Language language : values()) {
            if (language.getLanguageCode().equalsIgnoreCase(languageCode)) {
                return language;
            }
        }
        return null;
    }
}