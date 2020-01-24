package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word(R.drawable.family_father,
                "father",
                "әpә",
                R.raw.family_father));
        words.add(new Word(R.drawable.family_mother,
                "mother",
                "әṭa",
                R.raw.family_mother));
        words.add(new Word(R.drawable.family_son,
                "son",
                "angsi",
                R.raw.family_son));
        words.add(new Word(R.drawable.family_daughter,
                "daughter",
                "tune",
                R.raw.family_daughter));
        words.add(new Word(R.drawable.family_older_brother,
                "older brother",
                "aachi",
                R.raw.family_older_brother));
        words.add(new Word(R.drawable.family_younger_brother,
                "younger brother",
                "chalitti",
                R.raw.family_younger_brother));
        words.add(new Word(R.drawable.family_older_sister,
                "older sister",
                "teṭe",
                R.raw.family_older_sister));
        words.add(new Word(R.drawable.family_younger_sister,
                "younger sister",
                "kolliti",
                R.raw.family_younger_sister));
        words.add(new Word(R.drawable.family_grandmother,
                "grandmother",
                "ama",
                R.raw.family_grandmother));
        words.add(new Word(R.drawable.family_grandfather,
                "grandfather",
                "paapa",
                R.raw.family_grandfather));

        WordAdapter adapter = new WordAdapter(this, words,R.color.family);

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);

                Log.v("NumbersActivity", "Currennt word:" + word);
                mMediaPlayer = MediaPlayer.create(FamilyActivity.this,
                        word.getAudioResourceId());
                Toast.makeText(FamilyActivity.this,
                        "Play", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
