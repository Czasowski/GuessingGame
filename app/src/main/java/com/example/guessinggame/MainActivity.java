package com.example.guessinggame;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private EditText txtGuess;
    private Button btnGuess;
    private TextView lblOutput;
    private int theNumber;
    private int numberOfTries = 0;
    int numLeft1;
    String toastMessage;


    private void checkGuess() {
        numberOfTries++;
        numLeft1 = 10 - numberOfTries;
        String guessText = txtGuess.getText().toString();
        String message = "";
            try {
                int guess = Integer.parseInt(guessText);
                if (guess >= 100 || guess <= 0) {
                    Exception e = new Exception();
                    throw e;
                }
                if (guess < theNumber) {
                    message = guess + " is too low. Try again.";
                    toastMessage = "Left: " + numLeft1;
                    Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                } else if (guess > theNumber) {
                    numberOfTries++;
                    message = guess + " is too height. Try again.";
                    toastMessage = "Left: " + numLeft1;
                    Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                } else {
                    message = guess + " is correct. You win!";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    newGame();
                }
            } catch (Exception e) {
                message = "Enter a whole number between 1 and 100.";
            } finally {
                if (numLeft1 == 0) {
                    newGame();
                }
                lblOutput.setText(message);
                txtGuess.requestFocus();
                txtGuess.selectAll();
            }
        }





    public void newGame() {
        theNumber = (int) (Math.random()*100 + 1);
        numberOfTries = 0;
        toastMessage = "NEW GAME!";
        Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case R.id.action_settings:
            return true;
        case R.id.action_newgame:
            return true;
        case R.id.action_gamestats:
            return true;
        case R.id.action_about:
            AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
            aboutDialog.setTitle("About Guessing Game");
            aboutDialog.setMessage("(c)2019 Czasowski <3 Kicu");
            aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            aboutDialog.show();
            return true;
            default:
                return super.onOptionsItemSelected(item);
    }

    }
}
