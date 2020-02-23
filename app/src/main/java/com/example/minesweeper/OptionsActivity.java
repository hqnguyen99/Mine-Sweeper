package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class OptionsActivity extends AppCompatActivity {
    private static Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        game = Game.getInstance();

        createBoardSelectionButton();
        createMinesSelectionButton();
        setupEndButton();

    }

    private void createBoardSelectionButton() {
        RadioGroup boardSelectionGroup = (RadioGroup) findViewById(R.id.boardSelectionButton);
        int[] num_rows= getResources().getIntArray(R.array.number_of_rows);
        int[] num_columns= getResources().getIntArray(R.array.number_of_columns);

        // Create the buttons:
        for( int i = 0; i < num_rows.length; i++){
            final int ROW = num_rows[i];
            final int COLUMN = num_columns[i];
            RadioButton button = new RadioButton(this);
            button.setText(ROW + " x " + COLUMN);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    game.setBoardRow(ROW);
                    game.setBoardColumn(COLUMN);
                }
            });

            // Add to radio group
            boardSelectionGroup.addView(button);
        }
    }

    private void createMinesSelectionButton() {
        RadioGroup minesGroup = (RadioGroup) findViewById(R.id.minesSelectionButton);
        int[] num_mines = getResources().getIntArray(R.array.number_of_mines);

        //Create the button:
        for( int i =0 ; i < num_mines.length; i++){
            final int MINE = num_mines[i];
            RadioButton button = new RadioButton(this);
            button.setText(String.valueOf(MINE));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    game.setNumOfPikas(MINE);
                }
            });

            minesGroup.addView(button);
        }
    }

    private void setupEndButton() {
        Button endButton = (Button) findViewById(R.id.endButton);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
