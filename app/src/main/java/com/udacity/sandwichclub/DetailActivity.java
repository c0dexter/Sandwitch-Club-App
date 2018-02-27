package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getName();

    // Mapping active text views via "Butter Knife"
    @BindView(R.id.image_iv) ImageView sandwichImageView;
    @BindView(R.id.origin_tv) TextView placeOfOriginTextView;
    @BindView(R.id.also_known_tv) TextView alsoKnownAsTextView;
    @BindView(R.id.description_tv) TextView descriptionTextView;
    @BindView(R.id.ingredients_tv) TextView ingredientsTextView;

    // Initialize a sandwich object to manipulate it in every method
    private Sandwich sandwich;

    // Position of element on the list
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Bind views by Butter Knife
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = 0;
        if (intent != null) {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        // Set an image
        Picasso.with(this)
                .load(sandwich.getImage())
//                .placeholder(R.id.image_iv) // I can use some picture or progress bar
//                .error(R.id.image_iv) // I can show image when I get an error during loading image
                .into(sandwichImageView);
        setTitle(sandwich.getMainName());

        // Check if the "Place of origin" is not empty and populate new value
        if (!TextUtils.isEmpty(sandwich.getPlaceOfOrigin())) {
            placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        } else {
            placeOfOriginTextView.setText(R.string.missing_information);
            Log.i(LOG_TAG, "Place of origin is missing");
        }
        // Check if the "Description" is not empty and populate new value
        if (!TextUtils.isEmpty(sandwich.getDescription())) {
            descriptionTextView.setText(sandwich.getDescription());
        } else {
            descriptionTextView.setText(R.string.missing_information);
            Log.i(LOG_TAG, "Description is missing");
        }

        // Check if the "Also known as" is not empty and populate new value
        if (sandwich.getAlsoKnownAs().size() > 0) {
            List<String> alsoKnowAsList = sandwich.getAlsoKnownAs();
            alsoKnownAsTextView.setText(TextUtils.join(", ", alsoKnowAsList));
        } else {
            alsoKnownAsTextView.setText(R.string.missing_information);
            Log.i(LOG_TAG, "Other names of sandwich are missing");
        }

        // Check if "Ingredients" are not empty and populate new value
        if (sandwich.getIngredients().size() > 0) {
            List<String> ingredientsList = sandwich.getIngredients();
            ingredientsTextView.setText(TextUtils.join(", ", ingredientsList));
        } else {
            ingredientsTextView.setText(R.string.missing_information);
            Log.i(LOG_TAG, "Ingredients are missing");
        }
    }
}
