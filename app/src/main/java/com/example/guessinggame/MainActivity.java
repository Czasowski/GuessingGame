package com.example.guessinggame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private static final String MY_PREF = "MyPrefsFile";
    SharedPreferences pref;
    private int range = 100;
    private Button btnGuess;
    private EditText txtGuess;
    private TextView lblOutput;
    private int theNumber;
    private TextView lblRange;
    private int numberOfTries = 0;
    int numLeft1;
    String toastMessage;
    private int numOfTries;

    public int setNumOfTries(int rang) {
        switch (rang) {
            case 10:
                numOfTries = 5;
                break;
            case 100:
                numOfTries = 10;
                break;
            case 1000:
                numOfTries = 15;
                break;
        }
        return numOfTries;
    }


    private void checkGuess() {
        numberOfTries++;
        numLeft1 = numOfTries - numberOfTries;
        String guessText = txtGuess.getText().toString();
        String message = "";
            try {
                int guess = Integer.parseInt(guessText);
                if (guess >= range || guess <= 0) {
                    Exception e = new Exception();
                    throw e;
                }
                if (guess < theNumber) {
                    message = guess + " is too low. Try again.";
                    toastMessage = "Left: " + numLeft1;
                    Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                } else if (guess > theNumber) {
                    message = guess + " is too height. Try again.";
                    toastMessage = "Left: " + numLeft1;
                    Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                } else {
                    message = guess + " is correct. You win!";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    newGame();
                }
            } catch (Exception e) {
                message = "Enter a whole number between 1 and " + range + ":";
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
        theNumber = (int) (Math.random()* range + 1);
        setNumOfTries(range);
        numberOfTries = 0;
        toastMessage = "NEW GAME!";
        //Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtGuess = findViewById(R.id.txtGuess);
        lblOutput = findViewById(R.id.lblOutput);
        btnGuess = findViewById(R.id.btnGuess);
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
        lblRange = (TextView) findViewById(R.id.lblRange);
        pref = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        newGame();
    }
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case R.id.action_settings:
            final CharSequence[] items = {"Easy", "Medium", "Hard"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the Range:");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    switch (item) {
                        case 0:
                            range = 10;
                            storeRange(range);
                            newGame();
                            lblRange.setText("Enter a number between 1 and " + range + ":");
                            break;
                        case 1:
                            range = 100;
                            storeRange(range);
                            newGame();
                            lblRange.setText("Enter a number between 1 and " + range + ":");
                            break;
                        case 2:
                            range = 1000;
                            storeRange(range);
                            newGame();
                            lblRange.setText("Enter a number between 1 and " + range + ":");
                            break;
                    }
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        case R.id.action_newgame:
            newGame();
            return true;
        case R.id.action_gamestat:

            return true;
        case R.id.action_about:
            AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
            aboutDialog.setTitle("About Guessing Game");
            aboutDialog.setMessage("(c)2019 Czasowski <3 Kicu");
            aboutDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
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
    public void storeRange (int newRange) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("range", newRange);
        editor.commit();
        Toast.makeText(MainActivity.this,"Good", Toast.LENGTH_SHORT).show();
    }
}
