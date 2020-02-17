package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static Game game;
    private static int num_rows;
    private static int num_cols ;
    private static int num_bombs;

    private int numOfMinesFound = 0;
    private int numOfScans = 0;

    Button buttons[][];
    // 0 -> empty, -1 -> bomb
    private int grid[][];
    private boolean clicked[][];

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
        clicked = new boolean[num_rows][num_cols];

        populateButtons();
        setBombs();
        setInitialValues();
        buttonClicked();
    }

    private void setInitialValues() {
        for (int i = 0; i < num_rows; i++) {
            for (int j = 0; j < num_cols; j++) {
                if (grid[i][j] != -1) {
                    grid[i][j] = countBombs(i, j);
                }
            }
        }
    }

    private void populateButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
        TextView setScans = (TextView) findViewById(R.id.scans);
        TextView setMinesFound = (TextView) findViewById(R.id.minesFound);

        setScans.setText("# Scans Used: " + numOfScans);
        setMinesFound.setText("Found " + numOfMinesFound + " of " + num_bombs + " found.");

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
                clicked[row][col] = false;

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
        TextView setScans = (TextView) findViewById(R.id.scans);
        TextView setMinesFound = (TextView) findViewById(R.id.minesFound);

        Button b = buttons[r][c];
        int isBomb = grid[r][c];

        numOfScans++;


        if (isBomb == -1) {
            updateBombs(r, c);
            numOfMinesFound++;

            setScans.setText("# Scans Used: " + numOfScans);
            setMinesFound.setText("Found " + numOfMinesFound + " of " + num_bombs + " found.");
            b.setText("bomb!");

        } else {
            clicked[r][c] = true;
            setScans.setText("# Scans Used: " + numOfScans);
            b.setText(String.valueOf(grid[r][c]));
        }


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

    private void updateBombs(int r, int c) {
        grid[r][c] = 0;

        for (int i = 0; i < num_rows; i++) {
            if (grid[i][c] != -1) {
                grid[i][c] = countBombs(i, c);

                if (clicked[i][c]) {
                    buttons[i][c].setText(String.valueOf(grid[i][c]));
                }
            }
        }

        for (int j = 0; j < num_cols; j++) {
            if (grid[r][j] != -1) {
                grid[r][j] = countBombs(r, j);

                if (clicked[r][j]) {
                    buttons[r][j].setText(String.valueOf(grid[r][j]));
                }
            }
        }

        grid[r][c] = countBombs(r, c);

    }

}

