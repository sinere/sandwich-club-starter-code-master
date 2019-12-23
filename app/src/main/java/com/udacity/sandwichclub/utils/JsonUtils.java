package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();

        try {
            JSONObject sandwichJson = new JSONObject(json);
            sandwich.setMainName(sandwichJson.optString("mainName"));
            sandwich.setPlaceOfOrigin(sandwichJson.optString("placeOfOrigin"));
            sandwich.setDescription(sandwichJson.optString("description"));
            sandwich.setImage(sandwichJson.optString("image"));

            JSONObject nameJSONObject = sandwichJson.getJSONObject("name");
            JSONArray jsonArrayAKS = nameJSONObject.optJSONArray("alsoKnownAs");
            List<String> listAlsoKnownAs = new ArrayList<String>();
            if(jsonArrayAKS != null) {
                for (int i = 0; i < jsonArrayAKS.length(); i++) {
                    listAlsoKnownAs.add(jsonArrayAKS.getString(i));
                }

            }
            sandwich.setAlsoKnownAs(listAlsoKnownAs);

            JSONArray jsonArrayIngredients = sandwichJson.optJSONArray("ingredients");
            List<String> listIngredients = new ArrayList<String>();
            if(jsonArrayIngredients != null) {
                for (int i = 0; i < jsonArrayIngredients.length(); i++) {
                    listIngredients.add(jsonArrayIngredients.getString(i));
                }
            }
            sandwich.setIngredients(listIngredients);

        } catch (JSONException e) {
            System.out.println("Something went wrong.");
        }


        return sandwich;
    }
}