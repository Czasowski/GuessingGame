package com.example.guessinggame;

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
    SharedPreferences pref;
    private int range;
    private Button btnGuess;
    private EditText txtGuess;
    private TextView lblOutput;
    private int theNumber;
    private TextView lblRange;
    private int numberOfTries = 0;
    private int numLeft1;
    String toastMessage;
    private int numOfTries;
    final String RANGE = "range";
    private int gamesWon;
    private int lostGames;

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
                    message = guess + " is correct. You win! \n You could start a new game!";
                    pref = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor ed = pref.edit();
                    gamesWon = pref.getInt("GamesWin",0) + 1;
                    ed.putInt("GamesWin",gamesWon);
                    ed.apply();
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    newGame();
                }
            } catch (Exception e) {
                message = "Enter a whole number between 1 and " + range + ":";
            } finally {
                if (numLeft1 == 0) {
                    newGame();
                    pref = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor ed = pref.edit();
                    lostGames = pref.getInt("Lost",0) + 1;
                    ed.putInt("Lost",lostGames);
                    ed.apply();
                }
                lblOutput.setText(message);
                txtGuess.requestFocus();
                txtGuess.selectAll();
            }
        }





    public void newGame() {
        lblOutput.setText("Enter a number, then click Guess");
        txtGuess.setText("");
        lblRange.setText("Enter a number between 1 and " + range + ":");
        theNumber = (int) (Math.random()* range + 1);
        setNumOfTries(range);
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
        lblRange = findViewById(R.id.lblRange);
        pref = getPreferences(MODE_PRIVATE);
        range = pref.getInt(RANGE, 100);
        gamesWon = pref.getInt("GamesWin", 0);
        lostGames = pref.getInt("Lost",0);
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
                            break;
                        case 1:
                            range = 100;
                            storeRange(range);
                            newGame();
                            break;
                        case 2:
                            range = 1000;
                            storeRange(range);
                            newGame();
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
            AlertDialog statDialog = new AlertDialog.Builder(MainActivity.this).create();
            statDialog.setTitle("Your game statistic:");
            statDialog.setMessage("Win: " + gamesWon + "\n" + "Looses: " + lostGames);
            statDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            statDialog.show();
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
    private void storeRange (int newRange) {
        pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = pref.edit();
        ed.putInt(RANGE, newRange);
        ed.apply();
        Toast.makeText(MainActivity.this, "good", Toast.LENGTH_SHORT).show();
    }
}
