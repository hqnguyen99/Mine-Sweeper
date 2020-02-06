package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_ROWS = 4;
    private static final int NUM_COLS = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateButtons();
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
            }
        }
    }


}
