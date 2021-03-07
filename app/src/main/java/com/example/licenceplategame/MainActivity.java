package com.example.licenceplategame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public TextView searchResults;
    private List<String> words = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get texts
        EditText firstLetter = findViewById(R.id.firstLetter);
        EditText secondLetter = findViewById(R.id.secondLetter);
        EditText thirdLetter = findViewById(R.id.thirdLetter);
        searchResults = findViewById(R.id.search_results);

        //get button
        Button search_button = findViewById(R.id.search_button);

        //set on click listener for button
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hide keyboard if still showing
                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(findViewById(R.id.main_layout).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                //find matching words if not all letters are empty
                if(firstLetter.getText().toString().length() == 1 &&
                   secondLetter.getText().toString().length() == 1 &&
                   thirdLetter.getText().toString().length() == 1) {
                    //searchResults.setText(getWords(firstLetter.getText().toString().charAt(0), secondLetter.getText().toString().charAt(0), thirdLetter.getText().toString().charAt(0)));

                    String ret = "";
                    int limit = 20;
                    int count = 0;

                    for(int i = 0; i < words.size(); i++) {
                        boolean found1 = false;
                        boolean found2 = false;
                        boolean found3 = false;


                        int j = 0;
                        String word = words.get(i);

                        for(; j < word.length(); j++) {
                            if(word.charAt(j) == firstLetter.getText().toString().charAt(0)) {
                                found1 = true;
                            }
                        }

                        for(; j < word.length(); j++) {
                            if(word.charAt(j) == secondLetter.getText().toString().charAt(0)) {
                                found2 = true;
                            }
                        }

                        for(; j < word.length(); j++) {
                            if(word.charAt(j) == thirdLetter.getText().toString().charAt(0)) {
                                found3 = true;
                            }
                        }

                        if(found1 && found2 && found3) {
                            ret += word;
                            count++;
                        }

                        if(count == limit) { break; }
                    }
                    Log.d("done", ret);

                    searchResults.setText(ret);
                }

            }
        });

        //set focus on 2nd letter input if 1st is entered
        firstLetter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(firstLetter.getText().toString().length() == 1) {
                    secondLetter.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //set view on third letter when 2nd is input
        secondLetter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(secondLetter.getText().toString().length() == 1) {
                    thirdLetter.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //declare reader for reading word file
        BufferedReader reader = null;
        try {
            //assign reader
            reader = new BufferedReader(new InputStreamReader(getAssets().open("dictionary.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                words.add(mLine);
            }

        } catch (IOException e) {
            Log.e("Reading Input FIle", e.toString());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
            Log.d("words", "all loaded");
        }

        
    }

    //getWords searches the list of words for matches of the letters
    private String getWords(char l1, char l2, char l3) {
        //declare return variable
        String ret = "";
        int limit = 20;
        int count = 0;

        for(int i = 0; i < words.size(); i++) {
            boolean found1 = false;
            boolean found2 = false;
            boolean found3 = false;

            int j = 0;
            String word = words.get(i);

            for(; j < word.length(); j++) {
                if(word.charAt(j) == l1) {
                    found1 = true;
                }
            }

            for(; j < word.length(); j++) {
                if(word.charAt(j) == l2) {
                    found2 = true;
                }
            }

            for(; j < word.length(); j++) {
                if(word.charAt(j) == l3) {
                    found3 = true;
                }
            }

            if(found1 && found2 && found3) {
                ret += word;
                count++;
            }

            if(count == limit) { break; }
        }


        return ret;
    }
}