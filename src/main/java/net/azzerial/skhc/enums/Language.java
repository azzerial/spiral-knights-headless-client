/*
 * Copyright 2025 Robin Mercier (azzerial)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.azzerial.skhc.enums;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

/**
 * Enum representing the different game languages of <a href="https://www.spiralknights.com">Spiral Knights</a>.
 */
public enum Language {
    /** The {@code English} language. */
    ENGLISH("en", true),
    /** The {@code Spanish} language. */
    SPANISH("es"),
    /** The {@code German} language. */
    GERMAN("de"),
    /** The {@code French} language. */
    FRENCH("fr");

    private final String code;
    private final boolean isDefault;

    /* Constructors */

    Language(String code) {
        this(code, false);
    }

    Language(String code, boolean isDefault) {
        this.code = code;
        this.isDefault = isDefault;
    }

    /* Getters & Setters */

    /**
     * Get <a href="https://www.spiralknights.com">Spiral Knights</a> default game language.
     *
     * @return The default Language.
     *
     * @see #ENGLISH
     */
    @NotNull
    public static Language getDefault() {
        return ENGLISH;
    }

    /**
     * The internal code used to represent a language.
     *
     * @return The internal code of this Language.
     */
    @NotNull
    public String getCode() {
        return code;
    }

    /**
     * Whether this Language is the default one.
     *
     * @return {@code true} if this Language is the default one, otherwise {@code false}.
     */
    public boolean isDefault() {
        return isDefault;
    }

    /* Methods */

    /**
     * Retrieve the Language based on the provided {@link Locale}.
     *
     * @param  locale
     *         The {@link Locale} relating to the Language we wish to retrieve.
     *
     * @return The Language matching the code, otherwise if there is no match returns {@code null}.
     */
    @Nullable
    public static Language fromLocale(@Nullable Locale locale) {
        return locale == null ? null : fromCode(locale.getLanguage());
    }

    /**
     * Retrieve the Language based on the provided code.
     *
     * @param  code
     *         The code relating to the Language we wish to retrieve.
     *
     * @return The Language matching the code, otherwise if there is no match returns {@code null}.
     */
    @Nullable
    public static Language fromCode(@Nullable String code) {
        if (code == null) {
            return null;
        }
        for (Language language : values()) {
            if (language.code.equalsIgnoreCase(code)) {
                return language;
            }
        }
        return null;
    }
}