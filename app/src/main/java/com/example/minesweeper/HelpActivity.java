package com.example.minesweeper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        setText();
        setupEndButton();
    }

    private void setText() {
        TextView text = (TextView) findViewById(R.id.textInfo);

        text.setText("Welcome to the MineSeeker Game! " +
                "The objective of this game is to find all the bombs/mines located " +
                "in the 2d grid. Once a cell is clicked, it will either display a bomb " +
                "if it exists in the cell, or it will display a number that indicates " +
                "the number of bombs in the whole row and column. Try to get the least amount " +
                "of scans while locating all the bombs to win the game! Good luck!");
    }

    private void setupEndButton() {
        Button endButton = (Button) findViewById(R.id.helpEndButton);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
