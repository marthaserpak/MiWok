package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

public class ColorsActivity extends AppCompatActivity {

    MediaPlayer mMediaPlayer;

    AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                }
            };

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

        /*Create and setup the audioManager to request audio focus */
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

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

                /* Request audio focus for playback */
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    /* We have audio focus now.
                     *
                     * Create and setup the MediaPlayer for the audio resource
                     * associated with the current word */
                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this,
                            word.getAudioResourceId());

                    /* Start the audio file */
                    mMediaPlayer.start();

                    /* Setup the listener on the MediaPlayer, so that we can stop
                    * and release the media player once the sound has
                    * finished playing. */
                    mMediaPlayer.setOnCompletionListener(mOnCompletionListener);

                    Toast.makeText(ColorsActivity.this,
                            "Play", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*  When the activity is stopped, release the media player resources
         * because we won't be playing any more sounds*/
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();

            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
