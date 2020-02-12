package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static Game game;
    private static int num_rows;
    private static int num_cols ;
    private static int num_bombs;

    Button buttons[][];
    private int grid[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = Game.getInstance();
        num_rows = game.getBoardRow();
        num_cols = game.getBoardColumn();
        num_bombs = game.getNumberOfMines();
        buttons = new Button[num_rows][num_cols];
        grid = new int[num_rows][num_cols];

        populateButtons();
        setBombs();
        setValues();
        buttonClicked();
    }

    private void populateButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);

        for(int row = 0; row < num_rows; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,

                    1.0f));
            table.addView(tableRow);

            for(int col = 0; col < num_cols; col++){

                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                tableRow.addView(button);
                buttons[row][col] = button;
                grid[row][col] = 0;

            }
        }
    }

    private void buttonClicked() {
        for (int r = 0; r < num_rows; r++) {
            for (int c = 0; c < num_cols; c++) {
                Button button = buttons[r][c];
                final int row = r;
                final int col = c;

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridButtonClicked(row, col);
                    }
                });
            }
        }
    }

    private void gridButtonClicked(int r, int c) {
        Button b = buttons[r][c];
        int isBomb = grid[r][c];

        //lockButtonSizes();

        // put in if-else
        if (isBomb == -1) {
            updateBombs(r, c);
            b.setText("bomb!");

            // update the status of all clicked buttons
        } else if (isBomb == -2) {
            grid[r][c] = countBombs(r, c);
            b.setText(String.valueOf(grid[r][c]));
        } else {
            b.setText(String.valueOf(grid[r][c]));
        }

        // generate image
        // check the row + col and count
    }

    private int countBombs(int r, int c) {
        int counter = 0;

        for (int i = 0; i < num_rows; i++) {
            if(grid[i][c] == -1) {
                counter++;
            }
        }

        for (int j = 0; j < num_cols; j++) {
            if (grid[r][j] == -1) {
                counter++;
            }
        }


        return counter;

    }

    // check overlapped areas
    private void setBombs() {
        for (int i = 0; i < num_bombs; i++) {
            Random rand = new Random();
            int r = rand.nextInt(num_rows);
            int c = rand.nextInt(num_cols);

            // fixes overlapped positions
            while (grid[r][c] == -1) {
                r = rand.nextInt(num_rows);
                c = rand.nextInt(num_cols);
            }

            grid[r][c] = -1;

        }

    }

    private void setValues() {
        for (int i = 0; i < num_rows; i++) {
            for (int j = 0; j < num_cols; j++) {
                if (grid[i][j] != -1) {
                    grid[i][j] = countBombs(i, j);
                }
            }
        }
    }

    private void updateBombs(int r, int c) {
        for (int i = 0; i < num_rows; i++) {
            grid[i][c] = countBombs(i, c);
        }

        for (int j = 0; j < num_cols; j++) {
            grid[r][j] = countBombs(r, j);
        }

        grid[r][c] = -2;
    }

}

