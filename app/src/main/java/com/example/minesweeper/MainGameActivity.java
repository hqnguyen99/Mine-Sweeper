package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;


// main class that initiates the actual game
// calls the Game class which gets information about the board (ex. # rows + cols)
// handles all game logic
public class MainGameActivity extends AppCompatActivity {
    private static Game game;
    private static int numRows;
    private static int numCols;
    private static int totalPikas;

    private int numOfPikasFound = 0;
    private int numOfScans = 0;

    private Button buttons[][];

    private int values[][];
    // 0 -> empty, -1 -> bomb, any other num -> num of pikachu's in row + col

    private boolean clicked[][];
    // checks if cell has been clicked before
    // used to keep track of which values to display

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = Game.getInstance();
        numRows = game.getBoardRow();
        numCols = game.getBoardColumn();
        totalPikas = game.getNumOfPikas();
        buttons = new Button[numRows][numCols];
        values = new int[numRows][numCols];
        clicked = new boolean[numRows][numCols];

        populateButtons();
        setPikas();
        setInitialValues();
        checkButtonClicked();

    }


    private void populateButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
        TextView setScans = (TextView) findViewById(R.id.scans);
        TextView setMinesFound = (TextView) findViewById(R.id.minesFound);

        setScans.setText("# Scans Used: " + numOfScans);
        setMinesFound.setText("Found " + numOfPikasFound + " of " + totalPikas + " Pikachu.");

        for (int row = 0; row < numRows; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,

                    1.0f));
            table.addView(tableRow);

            for (int col = 0; col < numCols; col++) {

                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                tableRow.addView(button);
                buttons[row][col] = button;
                values[row][col] = 0;
                clicked[row][col] = false;

            }
        }
    }

    private void setPikas() {
        for (int i = 0; i < totalPikas; i++) {
            Random rand = new Random();
            int r = rand.nextInt(numRows);
            int c = rand.nextInt(numCols);

            // fixes overlapped positions
            while (values[r][c] == -1) {
                r = rand.nextInt(numRows);
                c = rand.nextInt(numCols);
            }

            values[r][c] = -1;

        }

    }

    // sets the values for all the pikachu in row + col at the start
    private void setInitialValues() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (values[i][j] != -1) {
                    values[i][j] = countPikas(i, j);
                }
            }
        }
    }

    private void checkButtonClicked() {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
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
        TextView setPikasFound = (TextView) findViewById(R.id.minesFound);

        Button button = buttons[r][c];

        lockButtonSizes();


        // found a pikachu
        if (values[r][c] == -1) {
            MediaPlayer pikachuSound = MediaPlayer.create(MainGameActivity.this, R.raw.pikachu_sound);
            pikachuSound.start();
            scanAnimation(r, c);
            updatePikas(r, c);
            numOfPikasFound++;

            setScans.setText("# Scans Used: " + numOfScans);
            setPikasFound.setText("Found " + numOfPikasFound + " of " + totalPikas + " Pikachu.");

            // set pikachu image as per Dr. Fraser's videos
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pikachu_icon);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));

            // found the last pikachu
            if (numOfPikasFound == totalPikas) {
                LinearLayout foo = new LinearLayout(this);
                revealAllCells();
                showPopup(foo);


            }

        } else {
            MediaPlayer scanSound = MediaPlayer.create(MainGameActivity.this, R.raw.scan_sound);
            scanSound.start();
            clicked[r][c] = true;
            numOfScans++;
            scanAnimation(r, c);
            setScans.setText("# Scans Used: " + numOfScans);
            button.setText(String.valueOf(values[r][c]));
        }


    }

    // as followed from Dr. Fraser's youtube video
    private void lockButtonSizes() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    private int countPikas(int r, int c) {
        int counter = 0;

        for (int i = 0; i < numRows; i++) {
            if(values[i][c] == -1) {
                counter++;
            }
        }

        for (int j = 0; j < numCols; j++) {
            if (values[r][j] == -1) {
                counter++;
            }
        }


        return counter;

    }

    private void updatePikas(int r, int c) {
        values[r][c] = 0;

        for (int i = 0; i < numRows; i++) {

            if (values[i][c] != -1) {
                values[i][c] = countPikas(i, c);

                if (clicked[i][c]) {
                    buttons[i][c].setText(String.valueOf(values[i][c]));
                }
            }
        }

        for (int j = 0; j < numCols; j++) {

            if (values[r][j] != -1) {
                values[r][j] = countPikas(r, j);

                if (clicked[r][j]) {
                    buttons[r][j].setText(String.valueOf(values[r][j]));
                }
            }
        }

        values[r][c] = countPikas(r, c);

    }

    private void scanAnimation(int row, int col) {
        @SuppressLint("ResourceType") Animation shake = AnimationUtils.loadAnimation(this, R.xml.shake);

        for (int i = 0; i < numRows; i++) {
            buttons[i][col].startAnimation(shake);
        }

        for (int j = 0; j < numCols; j++) {
            buttons[row][j].startAnimation(shake);
        }
    }

    public void showPopup(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        // note: the view passed in doesn't matter
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                finish();

                return true;
            }
        });





    }

    private void revealAllCells() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                buttons[i][j].setText(String.valueOf(values[i][j]));
            }
        }
    }

}

