package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;

public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    /*Handles audio focus when playing a sound file */
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        /* The AUDIOFOCUS_GAIN case means we've lost audio focus
                        and can Resume playback */
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        /* The AUDIOFOCUS_LOSS case means we've lost audio focus
                         * and stop playback and cleanup resources */
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

        WordAdapter adapter = new WordAdapter(this, words, R.color.family);

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                Log.v("NumbersActivity", "Currennt word:" + word);

                /* Release the media player if it currently exists because we are
                 * about to play different sound file*/
                releaseMediaPlayer();

                /* Request audio focus for playback */
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        /* Use the music stream */
                        AudioManager.STREAM_MUSIC,
                        /* Request permanent focus */
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    /* We have  audio focus now
                     Create and setup the {@link MediaPlayer} for the audio resource associated
                    with the current word*/
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this,
                            word.getAudioResourceId());

                    mMediaPlayer.start();

                    /* Setup a listener on M.P. ,so that we can stop and release the
                     * media player once the sound has finished playing */
                    mMediaPlayer.setOnCompletionListener(mOnCompletionListener);

                    Toast.makeText(FamilyActivity.this,
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

    /* Clean up media player by releasing its resources*/
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            /* Regardless of tge current state of the media player, release its resource
             * because we no longer need it*/
            mMediaPlayer.release();

            /* Set the media player back to null. For our code , we've decided that
             * setting the media player to null is an easy way to tell that the
             * media player is not configured to play an audio file at the moment */
            mMediaPlayer = null;

            /* Regardless of whether or not we were granted audio focus, abandon it.
             * This also unregisters the AudioFocusChangeListener so we don't get
             * anymore callbacks */
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
