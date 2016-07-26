package com.example.oshea.myfirstapp;


//import android.Manifest;
import android.content.Context;
import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;     //For accessing our textViews
import android.widget.Toast; //For 'visual debugging' on phone
import android.util.Log;
import java.util.Random;
import android.os.Vibrator; //For vibrations

//For Send Score
import android.telephony.SmsManager;
//import android.view.View.OnClickListener;
import android.widget.EditText;
//import android.app.Activity;
import android.app.AlertDialog;

public class MyActivity extends AppCompatActivity {

    int correct;
    int guesscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if (savedInstanceState != null) {
            correct = savedInstanceState.getInt("correct");
            guesscount = savedInstanceState.getInt("guesscount");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("correct", correct);
        outState.putInt("guesscount", guesscount);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        correct = savedInstanceState.getInt("correct");
        guesscount = savedInstanceState.getInt("guesscount");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void test_guess(View view) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //1. GET INPUT
        TextView textView = (TextView) findViewById(R.id.editText); //Obtain the TextView element where the user provides the input
        String str = textView.getText().toString(); //Get the text currently assigned to this input. (Since it is of inputType number we are assured that there will only be pos ints)
        //2. TEST INPUT
        if (!str.isEmpty()) { //Check that there is input
            Log.d("test_guess", "Testing guess" + str);
            int i = Integer.parseInt(str); //Turn the string into an int
            //2.1 TEST PRIMENESS
            boolean guess = isGuess(i);
            //2.2 REPORT THE RESULT
            TextView textView2 = (TextView) findViewById(R.id.textView2);
            TextView textView3 = (TextView) findViewById(R.id.textView3);
            TextView textView4 = (TextView) findViewById(R.id.textView4);

            if (guess) {
                correct++;
                guesscount++;
                textView2.setText(i + " is Correct!");
                textView3.setText(correct + " Correct Guesses");
                textView4.setText(guesscount + " Guesses");
                // Vibrate for 400 milliseconds
                v.vibrate(400);
            } else {
                guesscount++;
                textView2.setText(i + " is not Correct");
                textView4.setText(guesscount + " Guesses");
                textView3.setText(correct + " Correct Guesses");
            }

            Log.d("test_guess", i + " -> guess:" + guess);
        } else {//The user could have pressed the button without providing any number.
            Log.d("test_guess", "No number was provided");
            Toast.makeText(this, "No number provided", Toast.LENGTH_LONG).show();
        }
    }

    //Tests if a number is prime
    private boolean isGuess(int i) {
        boolean flag = true;//All inputs are considered true until proven wrong
        Random rand = new Random();
        int randomNum = rand.nextInt((9 - 0) + 1);
        if (i != randomNum) {
            flag = false;
        }
        return flag;
    }

    public void send_score(View v) {
        String phoneNumber = ((EditText) findViewById(R.id.editText2)).getText().toString();
        String score = "" + correct + " out of " + guesscount + " guesses!";
        try {
            SmsManager.getDefault().sendTextMessage(phoneNumber, null, score, null, null);
        } catch (Exception e) {
            AlertDialog.Builder alertDialogBuilder = new

                    AlertDialog.Builder(this);

            AlertDialog dialog = alertDialogBuilder.create();

            dialog.setMessage(e.getMessage());

            dialog.show();

        }
    }
}