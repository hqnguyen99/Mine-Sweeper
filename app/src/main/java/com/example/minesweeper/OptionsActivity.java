package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        createBoardSelectionButton();
        createMinesSelectionButton();
    }

    private void createBoardSelectionButton() {
        RadioGroup boardSelectionGroup = (RadioGroup) findViewById(R.id.boardSelectionButton);
        int[] num_rows= getResources().getIntArray(R.array.number_of_rows);
        int[] num_columns= getResources().getIntArray(R.array.number_of_columns);

        // Create the buttons:
        for( int i =0; i < num_rows.length; i++){
            int row = num_rows[i];
            int column = num_columns[i];
            RadioButton button = new RadioButton(this);
            button.setText(row + " x " + column);

            // Add to radio group
            boardSelectionGroup.addView(button);
        }
    }

    private void createMinesSelectionButton() {
        RadioGroup minesGroup = (RadioGroup) findViewById(R.id.minesSelectionButton);
        int[] num_mines = getResources().getIntArray(R.array.number_of_mines);

        //Create the button:
        for( int i =0 ; i < num_mines.length; i++){
            int mine = num_mines[i];
            RadioButton button = new RadioButton(this);
            button.setText(mine);

            minesGroup.addView(button);
        }
    }
}
