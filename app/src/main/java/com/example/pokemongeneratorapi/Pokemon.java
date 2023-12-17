package com.example.pokemongeneratorapi;
//step 3:
//SerializedName buat key dalam API nya
import com.google.gson.annotations.SerializedName;

//Buat class PokemonSprites buat image nya
class PokemonSprites {
    @SerializedName("front_default")
    public String frontDefault;
    public String getFrontDefault() {
        return frontDefault;
    }
}

//class pokemon buat simpen object dari API
public class Pokemon {
    @SerializedName("name")
    public String name;

    @SerializedName("height")
    public int height;

    @SerializedName("sprites")
    public PokemonSprites sprites; // Assume you have a PokemonSprites class

    @SerializedName("weight")
    public int weight;

    // *No-argument constructor for Gson
    public Pokemon() {
    }

    // Add getter methods for fields
    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public String getSprites() {
        return sprites.getFrontDefault();
    }


}
