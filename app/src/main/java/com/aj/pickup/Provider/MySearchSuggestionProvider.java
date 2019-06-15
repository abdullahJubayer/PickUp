package com.aj.pickup.Provider;

import android.content.SearchRecentSuggestionsProvider;

public class MySearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.aj.pickup.MySearchSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySearchSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
