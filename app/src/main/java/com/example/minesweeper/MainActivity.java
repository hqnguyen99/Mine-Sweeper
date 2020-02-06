package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_ROWS = 4;
    private static final int NUM_COLS = 6;
    private int numBombs = 5;
    Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
    private boolean grid[][] = new boolean[NUM_ROWS][NUM_COLS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateButtons();
        setBombs();
        buttonClicked();
    }

    private void populateButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);

        for(int row = 0; row < NUM_ROWS; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,

                    1.0f));
            table.addView(tableRow);

            for(int col = 0; col < NUM_COLS; col++){

                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                1.0f));

                tableRow.addView(button);
                buttons[row][col] = button;
                grid[row][col] = false;
            }
        }
    }

    private void buttonClicked() {
        for (int r = 0; r < NUM_ROWS; r++) {
            for (int c = 0; c < NUM_COLS; c++) {
                Button button = buttons[r][c];
                final int FINAL_ROW = r;
                final int FINAL_COL = c;

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridButtonClicked(FINAL_ROW, FINAL_COL);
                    }
                });
            }
        }
    }

    private void gridButtonClicked(int r, int c) {
        Button b = buttons[r][c];
        boolean isBomb = grid[r][c];

        // put in if-else
        if (isBomb) {
            grid[r][c] = false;
            b.setText("bomb!");

            // update the status of all clicked buttons
        } else {
            int bombs = countBombs(r, c);
            String strBombs = String.valueOf(bombs);
            b.setText(strBombs);
        }

        // generate image
        // check the row + col and count
    }

    private int countBombs(int r, int c) {
        int counter = 0;

        for (int i = 0; i < r; i++) {
            if (grid[i][c]) {
                counter++;
            }
        }

        for (int j = 0; j < c; j++) {
            if (grid[r][j]) {
                counter++;
            }
        }


        return counter;

    }

    // check overlapped areas
    private void setBombs() {
        for (int i = 0; i < numBombs; i++) {
            Random rand = new Random();
            int r = rand.nextInt(NUM_ROWS);
            int c = rand.nextInt(NUM_COLS);

            // fixes overlapped positions
            while (grid[r][c]) {
                r = rand.nextInt(NUM_ROWS);
                c = rand.nextInt(NUM_COLS);
            }

            grid[r][c] = true;

        }

    }

}
