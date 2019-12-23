package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView textViewOrigin = findViewById(R.id.origin_tv);
        textViewOrigin.setText(sandwich.getPlaceOfOrigin());

        TextView textViewDescription = findViewById(R.id.description_tv);
        textViewDescription.setText(sandwich.getDescription());

        ImageView imageView = findViewById(R.id.image_iv);
        String image = sandwich.getImage();
        int resID = getResources().getIdentifier(image, "drawable", getPackageName());
        imageView.setImageResource(resID);

        TextView textViewAlsoKnown = findViewById(R.id.also_known_tv);
        String otherNamesValues = "";
        for (String value : sandwich.getAlsoKnownAs()) {
            otherNamesValues += value + ", ";
        }
        if (otherNamesValues.length() > 1) {
            otherNamesValues = otherNamesValues.substring(0, (otherNamesValues.length() - 2));
        }
        textViewAlsoKnown.setText(otherNamesValues);

        TextView textViewIngredients = findViewById(R.id.ingredients_tv);
        String ingredientsValues = "";
        for (String value : sandwich.getIngredients()) {
            ingredientsValues += value + ", ";
        }
        if (ingredientsValues.length() > 1) {
            ingredientsValues = ingredientsValues.substring(0, (ingredientsValues.length() - 2));
        }
        textViewIngredients.setText(ingredientsValues);
    }
}
