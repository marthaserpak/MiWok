package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView numbers = findViewById(R.id.numbers);
        TextView colors = findViewById(R.id.colors);
        TextView family = findViewById(R.id.family);
        TextView phrases = findViewById(R.id.phrases);

        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent numInt = new Intent(MainActivity.this,
                        NumbersActivity.class);
                startActivity(numInt);
            }});

        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent colInt = new Intent(MainActivity.this,
                        ColorsActivity.class);
                startActivity(colInt);
            }
        });

        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent famInt = new Intent(MainActivity.this,
                        FamilyActivity.class);
                startActivity(famInt);
            }
        });

        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phrInt = new Intent(MainActivity.this,
                        PhrasesActivity.class);
                startActivity(phrInt);
            }
        });
    }

}
