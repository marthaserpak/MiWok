package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    MediaPlayer mMediaPlayer;

    /* This listener gets triggered when the mediaplayer
    has completed playing the audio*/
    private MediaPlayer.OnCompletionListener mOnCompletionListener =
            new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    releaseMediaPlayer();
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word(R.drawable.color_red,
                "red",
                "weṭeṭṭi",
                R.raw.color_red));
        words.add(new Word(R.drawable.color_green,
                "green",
                "chokokki",
                R.raw.color_green));
        words.add(new Word(R.drawable.color_brown,
                "brown",
                "ṭakaakki",
                R.raw.color_brown));
        words.add(new Word(R.drawable.color_gray,
                "gray",
                "ṭopoppi",
                R.raw.color_gray));
        words.add(new Word(R.drawable.color_black,
                "black",
                "kululli",
                R.raw.color_black));
        words.add(new Word(R.drawable.color_white,
                "white",
                "kelelli",
                R.raw.color_white));
        words.add(new Word(R.drawable.color_dusty_yellow,
                "dusty yellow",
                "ṭopiisә",
                R.raw.color_dusty_yellow));
        words.add(new Word(R.drawable.color_mustard_yellow,
                "mustard yellow",
                "chiwiiṭә",
                R.raw.color_mustard_yellow));

        WordAdapter adapter = new WordAdapter(this, words, R.color.colors);

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);

                releaseMediaPlayer();

                mMediaPlayer = MediaPlayer.create(ColorsActivity.this,
                        word.getAudioResourceId());

                mMediaPlayer.start();

                mMediaPlayer.setOnCompletionListener(mOnCompletionListener);

                Toast.makeText(ColorsActivity.this,
                        "Play", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void releaseMediaPlayer(){
        if(mMediaPlayer != null) {
            mMediaPlayer.release();

            mMediaPlayer = null;
        }
    }
}
