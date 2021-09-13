package com.psiu.helloworld.service;

import android.os.AsyncTask;

import com.psiu.helloworld.model.Card;
import com.psiu.helloworld.model.CardValue;
import com.psiu.helloworld.model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class CardService extends AsyncTask<Void, Void, Card> {

    @Override
    protected Card doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
        Card card_value = new Card();

        try {
            URL url = new URL("https://raw.githubusercontent.com/policante/Coopercard-mobile/master/service/cards.json");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(5000);
            connection.connect();

            Scanner scanner = new Scanner(url.openStream());
            while(scanner.hasNext()){
                resposta.append(scanner.next());
            }

            JSONObject obj = new JSONObject(resposta.toString());
            JSONArray array = obj.getJSONArray("cards");


            ArrayList<CardValue> array_cards = new ArrayList<>();

            for(int i = 0; i < array.length(); ++i){
                CardValue cardValue = new CardValue();
                cardValue.setName(array.getJSONObject(i).getString("name"));
                cardValue.setCard_number(array.getJSONObject(i).getString("card_number"));
                cardValue.setLimit(array.getJSONObject(i).getLong("limit"));

                Category categoria = new Category();
                categoria.setBackground_color(array.getJSONObject(i).getJSONObject("category").getString("background_color"));
                categoria.setImage_path(array.getJSONObject(i).getJSONObject("category").getString("image_path"));
                categoria.setType(array.getJSONObject(i).getJSONObject("category").getString("type"));

                cardValue.setCategory(categoria);
                array_cards.add(cardValue);
            }
            card_value.setCards(array_cards);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return card_value;
    }
}
