package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<>();

        words.add(new Word(R.drawable.family_father,"father", "әpә"));
        words.add(new Word(R.drawable.family_mother,"mother", "әṭa"));
        words.add(new Word(R.drawable.family_son,"son", "angsi"));
        words.add(new Word(R.drawable.family_daughter,"daughter", "tune"));
        words.add(new Word(R.drawable.family_older_brother,"older brother", "aachi"));
        words.add(new Word(R.drawable.family_younger_brother,"younger brother", "chalitti"));
        words.add(new Word(R.drawable.family_older_sister,"older sister", "teṭe"));
        words.add(new Word(R.drawable.family_younger_sister,"younger sister", "kolliti"));
        words.add(new Word(R.drawable.family_grandmother,"grandmother", "ama"));
        words.add(new Word(R.drawable.family_grandfather,"grandfather", "paapa"));

        WordAdapter adapter = new WordAdapter(this, words,R.color.family);

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(adapter);
    }
}
