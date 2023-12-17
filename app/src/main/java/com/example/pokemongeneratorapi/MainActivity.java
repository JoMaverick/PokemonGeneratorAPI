package com.example.pokemongeneratorapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
//Step 5: bikin main nya
public class MainActivity extends AppCompatActivity {
    //init variable yang perlu nya
    String pokeurl = "https://pokeapi.co/api/v2/pokemon/";
    Context ctx;
    Button generateBtn;
    Pokemon pokemon;
//bikin button nya
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx=this;
        generateBtn = findViewById(R.id.btnGenerateRandomPokemon);
        //diawal generate 1 dl
        GeneratePokemon init = new GeneratePokemon();
        init.execute();

        //setiap click button akan generate baru
        generateBtn.setOnClickListener(e->{
            GeneratePokemon gp = new GeneratePokemon();
            gp.execute();
        });
    }

    //function buat generate nya akan extends AsyncTask
    public class GeneratePokemon extends AsyncTask<Void, Void, String> {

        //readstream sebagai buffer dibuat dipisah agar lebih mudah
        private String readStream(InputStream is){
            try{
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int i = is.read();
                while(i!=-1){
                    buffer.write(i);
                    i= is.read();
                }
                return buffer.toString();
            }
            catch(Exception e){
                return "";
            }
        };

        //doInBackgorund method inheritance dari AsyncTask
        @Override
        protected String doInBackground(Void... voids) {
            // mulai ambil API
            String result = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    int randomNumber = new Random().nextInt(1000) + 1;
                    url = new URL(pokeurl + randomNumber);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    result = readStream(in);
                    urlConnection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            //return data (String - String) ke onPostExecute method
            return result;
        }

        //disini display semua data nya
        protected void onPostExecute(String result) {

            Log.v("server response", result);
            try {
                //ini buat masukin data ke object
                pokemon = PokemonParser.parsePokemon(result);
                // Print Pokemon details
                Log.v("Pokemon Details", "Name: " + pokemon.getName());
                Log.v("Pokemon Details", "Height: " + pokemon.getHeight());
                Log.v("Pokemon Details", "Weight: " + pokemon.getWeight());
                Log.v("Pokemon Details", "Image: " + pokemon.getSprites());  // asumsi 'image' = int
                //init TextView dan ImageView
                TextView name = findViewById(R.id.PokeName);
                TextView weight = findViewById(R.id.PokeWeight);
                TextView height = findViewById(R.id.PokeHeight);
                ImageView iv = findViewById(R.id.imgPokemon);
                String imageUrl = pokemon.getSprites();
                //glide buat bantuan load image
                Glide.with(ctx).load(imageUrl).into(iv);
                name.setText("Name: " + pokemon.getName());
                weight.setText("weight: " + pokemon.getWeight()); 
                height.setText("Height: " + pokemon.getHeight());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}