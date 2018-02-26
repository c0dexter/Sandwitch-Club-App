package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // Make logs by using log tag
    private static final String LOG_TAG = JsonUtils.class.getName();

    public static Sandwich parseSandwichJson(String json) {
        // Create new Sandwich object
        Sandwich sandwich = new Sandwich();

        // Variables for storing parsed data
        final String mainName;
        final String image;
        final String placeOfOrigin;
        final String description;
        final List<String> alsoKnownAsList = new ArrayList<>();
        final List<String> ingredientsList = new ArrayList<>();

        // String keys for manipulating on JSON object
        final String jasonObjectKeyNameName = "name";
        final String jasonObjectKeyMainName = "mainName";
        final String jasonObjectKeyAlsoKnownAsList = "alsoKnownAs";
        final String jasonObjectKeyPlaceOfOrigin = "placeOfOrigin";
        final String jasonObjectKeyDescription = "description";
        final String jasonObjectKeyImage = "image";
        final String jasonObjectKeyINGREDIENTS = "ingredients";

        try {
            // Initialize new JSON Object which will be needed for saving data from a string array
            JSONObject mainJsonObject = new JSONObject(json);

            if (mainJsonObject.has(jasonObjectKeyNameName)) {
                JSONObject nameJsonObject = mainJsonObject.getJSONObject(jasonObjectKeyNameName);
                if (nameJsonObject.has(jasonObjectKeyMainName)) {
                    mainName = nameJsonObject.optString(jasonObjectKeyMainName); // removed cast (String)
                    sandwich.setMainName(mainName);
                    Log.i(LOG_TAG, "Main name: " + mainName);
                }

                if (nameJsonObject.has(jasonObjectKeyAlsoKnownAsList)) {
                    JSONArray secondNameJsonArray = nameJsonObject.getJSONArray(jasonObjectKeyAlsoKnownAsList);
                    if (secondNameJsonArray.length() > 0) {
                        for (int i = 0; i < secondNameJsonArray.length(); i++) {
                            alsoKnownAsList.add(i, secondNameJsonArray.getString(i));
                        }
                    } else {
                        Log.i(LOG_TAG, "ArrayList of \"alsoKnownAs\" is empty");
                    }
                    sandwich.setAlsoKnownAs(alsoKnownAsList);
                }

                if (mainJsonObject.has(jasonObjectKeyPlaceOfOrigin)) {
                    placeOfOrigin = mainJsonObject.optString(jasonObjectKeyPlaceOfOrigin);
                    sandwich.setPlaceOfOrigin(placeOfOrigin);
                    Log.i(LOG_TAG, "Place of origin: " + placeOfOrigin);
                }

                if (mainJsonObject.has(jasonObjectKeyDescription)) {
                    description = mainJsonObject.optString(jasonObjectKeyDescription);
                    sandwich.setDescription(description);
                    Log.i(LOG_TAG, "Description: " + description);
                }

                if (mainJsonObject.has(jasonObjectKeyImage)) {
                    image = mainJsonObject.optString(jasonObjectKeyImage);
                    sandwich.setImage(image);
                    Log.i(LOG_TAG, "IMAGE URL: " + image);
                }

                if (mainJsonObject.has(jasonObjectKeyINGREDIENTS)) {
                    JSONArray ingredientsJsonArray = mainJsonObject.getJSONArray(jasonObjectKeyINGREDIENTS);
                    if (ingredientsJsonArray.length() > 0) {
                        for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                            ingredientsList.add(i, ingredientsJsonArray.getString(i));
                        }
                    } else {
                        Log.i(LOG_TAG, "ArrayList of ingredients is empty");
                    }
                    sandwich.setIngredients(ingredientsList);
                    Log.i(LOG_TAG + "IMAGE URL: ", ingredientsList.toString());
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getStackTrace().toString());
        }

        // Return a sandwich object which is containing full available data.
        return sandwich;
    }
}
