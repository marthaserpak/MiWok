package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

public class PhrasesActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    /* This listener gets triggered when the mediaplayer
    has completed playing the audio*/
    private MediaPlayer.OnCompletionListener mOnCompletionListener =
            new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    releaseMediaPlayer();
                }
            };

    private AudioManager.OnAudioFocusChangeListener mOnAudioChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        /* The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus
                         * for a short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK
                         * case means that our app is allowed to continue playing but at
                         * a lower volume. We'll treat both cases the same way because our app
                         * is playing short sound files.
                         *
                         * Pause playback and reset the player to the start of the file.
                         * The way, we can play the word from the beginning when we resume playback*/
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        /* AUDIOFOCUS_GAIN case means we have regained focus and
                        * can resume playback. */
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        /* The AUDIOFOCUS_LOSS case means we've loss audio focus
                        * and stop playback and clean up resources. */
                        releaseMediaPlayer();
                    }
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        /* Create and setup AudioManager to request audio focus. */
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("Where are you going?", "minto wuksus",
                R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә",
                R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is..", "oyaaset...",
                R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?",
                R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good.", "kuchi achit",
                R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?",
                R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I'm coming.", "hәә’ әәnәm",
                R.raw.phrase_yes_im_coming));
        words.add(new Word("I'm coming.", "әәnәm",
                R.raw.phrase_im_coming));
        words.add(new Word("Let's go.", "yoowutis",
                R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem",
                R.raw.phrase_come_here));

        WordAdapter adapter = new WordAdapter(this, words, R.color.phrases);

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);

                releaseMediaPlayer();

                /* Request audio focus so in order to play the audio file. The app needs to play a
                 * short audio file, so we will request audio focus with a short amount of time
                 * with AUDIOFOCUS_GAIN_TRANSIENT.*/
                int result = mAudioManager.requestAudioFocus(mOnAudioChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                /* We have audio focus now.
                 *
                 * Create and set up  the MediaPlayer for the audio rescource
                 * assotiated with the current word. */
                mMediaPlayer = MediaPlayer.create(PhrasesActivity.this,
                        word.getAudioResourceId());

                mMediaPlayer.start();
                /* Setup listener on the media player, so that
                 * we can stop and release the
                 * media player once the sound has finished playing. */
                mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
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


            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioChangeListener);
        }
    }
}
