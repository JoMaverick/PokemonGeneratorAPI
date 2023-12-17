package com.example.pokemongeneratorapi;
//step 4:
//pokemon parser buat mengubah hasil dari API berupa String ke Object dengan bantuan Gson
import com.google.gson.Gson;

public class PokemonParser {
    private static final Gson gson = new Gson();
    public static Pokemon parsePokemon(String json) {
        return gson.fromJson(json, Pokemon.class);
    }
}
