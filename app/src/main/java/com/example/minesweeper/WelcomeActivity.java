package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

// this is the animation screen
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setupSkipButton();
    }

    private void setupSkipButton() {
        final CountDownTimer timer;
        timer = new CountDownTimer(5000,1000){

            @Override
            public void onTick(long l) {

            }

            public void onFinish() {
                Intent intent = new Intent(WelcomeActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        }.start();

        Button skipButton = (Button) findViewById(R.id.skipButton);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();

                Intent intent = new Intent(WelcomeActivity.this,MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
