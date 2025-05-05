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

/**
 * Enum representing the different server regions of <a href="https://www.spiralknights.com">Spiral Knights</a>.
 */
public enum Region {
    /** The {@code us-east} server region. */
    US_EAST("us-east", true),
    /** The {@code eu-west} server region. */
    EU_WEST("eu-west");

    private final String code;
    private final boolean isDefault;

    /* Constructors */

    Region(String code) {
        this(code, false);
    }

    Region(String code, boolean isDefault) {
        this.code = code;
        this.isDefault = isDefault;
    }

    /* Getters & Setters */

    /**
     * Get <a href="https://www.spiralknights.com">Spiral Knights</a> default server region.
     *
     * @return The default Region.
     *
     * @see #US_EAST
     */
    @NotNull
    public static Region getDefault() {
        return US_EAST;
    }

    /**
     * The internal code used to represent the server region.
     *
     * @return The internal code of this Region.
     */
    @NotNull
    public String getCode() {
        return code;
    }

    /**
     * Whether this Region is the default one.
     *
     * @return {@code true} if this Region is the default one, otherwise {@code false}.
     */
    public boolean isDefault() {
        return isDefault;
    }

    /* Methods */

    /**
     * Retrieve the Region based on the provided code.
     *
     * @param  code
     *         The code relating to the Region we wish to retrieve.
     *
     * @return The Region matching the code, otherwise if there is no match returns {@code null}.
     */
    @Nullable
    public static Region fromCode(@Nullable String code) {
        if (code == null) {
            return null;
        }
        for (Region region : values()) {
            if (region.code.equalsIgnoreCase(code)) {
                return region;
            }
        }
        return null;
    }
}