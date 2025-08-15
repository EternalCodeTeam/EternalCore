package com.eternalcode.core.feature.near;

public interface NearSettings {

    /**
     * Returns the style for bullet points in the near command output.
     *
     * @return the bullet point style
     */
    String bulletPointStyle();
    /**
     * Returns the symbol used for bullet points in the near command output.
     *
     * @return the bullet point symbol
     */
    String bulletPointSymbol();
    /**
     * Returns the style for list items in the near command output.
     *
     * @return the list item style
     */
    String listItemStyle();

}
