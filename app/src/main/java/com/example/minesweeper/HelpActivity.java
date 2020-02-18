package com.example.minesweeper;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
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
        text.setMovementMethod(LinkMovementMethod.getInstance());
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
