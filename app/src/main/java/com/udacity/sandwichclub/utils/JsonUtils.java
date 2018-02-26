package com.udacity.sandwichclub.utils;

import android.content.Context;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // Make logs by using log tag
    private static final String LOG_TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {

        // Create new Sandwich object
        Sandwich sandwich = new Sandwich();

        // Variables for storing parsed data
        final String MAIN_NAME;
        final List<String> ALSO_KNOWN_AS = new ArrayList<>();
        final String PLACE_OF_ORIGIN;
        final String DESCRIPTION;
        final String IMAGE;
        final List<String> INGREDIENTS = new ArrayList<>();

        try {
            // Initialize new JSON Object which will be needed for saving data from a string array
            JSONObject mainJsonObject = new JSONObject(json);

            if (mainJsonObject.has("name")) {
                JSONObject nameJsonObject = mainJsonObject.getJSONObject("name");
                if (nameJsonObject.has("mainName")) {
                    MAIN_NAME = (String) nameJsonObject.opt("mainName");
                    sandwich.setMainName(MAIN_NAME);
                    Log.i(LOG_TAG, "Main name: " + MAIN_NAME);
                }

                if (nameJsonObject.has("alsoKnownAs")) {
                    JSONArray secondNameJsonArray = nameJsonObject.getJSONArray("alsoKnownAs");
                    if (secondNameJsonArray.length() > 0) {
                        for (int i = 0; i < secondNameJsonArray.length(); i++) {
                            ALSO_KNOWN_AS.add(i, secondNameJsonArray.getString(i));
                        }
                    } else {
                        Log.i(LOG_TAG, "ArrayList of \"alsoKnownAs\" is empty");
                    }
                    sandwich.setAlsoKnownAs(ALSO_KNOWN_AS);
                }

                if (mainJsonObject.has("placeOfOrigin")) {
                    PLACE_OF_ORIGIN = mainJsonObject.optString("placeOfOrigin");
                    sandwich.setPlaceOfOrigin(PLACE_OF_ORIGIN);
                    Log.i(LOG_TAG, "Place of origin: " + PLACE_OF_ORIGIN);
                }

                if (mainJsonObject.has("description")) {
                    DESCRIPTION = mainJsonObject.optString("description");
                    sandwich.setDescription(DESCRIPTION);
                    Log.i(LOG_TAG, "Description: " + DESCRIPTION);
                }

                if (mainJsonObject.has("image")) {
                    IMAGE = mainJsonObject.optString("image");
                    sandwich.setImage(IMAGE);
                    Log.i(LOG_TAG,"IMAGE URL: " + IMAGE);
                }

                if (mainJsonObject.has("ingredients")) {
                    JSONArray ingredientsJsonArray = mainJsonObject.getJSONArray("ingredients");
                    if (ingredientsJsonArray.length() > 0) {
                        for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                            INGREDIENTS.add(i, ingredientsJsonArray.getString(i));
                        }
                    } else {
                        Log.i(LOG_TAG, "ArrayList of ingredients is empty");
                    }
                    sandwich.setIngredients(INGREDIENTS);
                    Log.i(LOG_TAG + "IMAGE URL: ", INGREDIENTS.toString());
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getStackTrace().toString());
        }

        // Return a sandwich object which is containing full available data.
        return sandwich;
    }
}
