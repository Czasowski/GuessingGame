package com.example.guessinggame;

import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private EditText txtGuess;
    private Button btnGuess;
    private TextView lblOutput;
    private int theNumber;
    private int numberOfTries = 0;
    final private int numLeft = 10;
    int numLeft1;

    private void checkGuess() {
        String guessText = txtGuess.getText().toString();
        String message = "";
        try {
            while (numberOfTries <= 10) {
                int guess = Integer.parseInt(guessText);
                if (guess < theNumber) {
                    numberOfTries++;
                    numLeft1 = numLeft - numberOfTries;
                    message = guess + " is too low. Try again. \n Left: " + numLeft1;

                } else if (guess > theNumber) {
                    numberOfTries++;
                    int numLeft1 = numLeft - numberOfTries;
                    message = guess + " is too low. Try again. \n Left: " + numLeft1;
                } else {
                    message = guess + " is correct. You win!";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    newGame();
                }
            }
            } catch(Exception e){
                message = "Enter a whole number between 1 and 100.";
            } finally{
                lblOutput.setText(message);
                txtGuess.requestFocus();
                txtGuess.selectAll();
            }
        newGame();
        }


    public void newGame() {
        theNumber = (int) (Math.random()*100 + 1);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtGuess = findViewById(R.id.txtGuess);
        lblOutput = findViewById(R.id.lblOutput);
        btnGuess = findViewById(R.id.btnGuess);
        newGame();
        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess();
            }
        });
        txtGuess.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                checkGuess();
                return false;
            }
        });
    }
}
