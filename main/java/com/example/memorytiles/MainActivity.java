package com.example.memorytiles;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    final Handler handler = new Handler(Looper.getMainLooper());
    final Random rand = new Random();

    private int[] sequence = new int[4];
    private int size = 4;
    private int current = 0;
    private int score = 0;

    private void generateSequence() {
        for (int i = 0; i < size; ++i) {
            sequence[i] = rand.nextInt(9) + 1;
        }
    }

    private void addToSequence() {
        if (size == sequence.length) {
            int[] sequenceCopy = Arrays.copyOf(sequence, sequence.length * 2);
            sequence = sequenceCopy;
        }
        size += 1;
        sequence[size-1] = rand.nextInt(9) + 1;
    }

    private int getNumberFromButton(int id) {
        int result = -1;
        switch (id) {
            case R.id.button1:
                result = 1;
                break;
            case R.id.button2:
                result = 2;
                break;
            case R.id.button3:
                result = 3;
                break;
            case R.id.button4:
                result = 4;
                break;
            case R.id.button5:
                result = 5;
                break;
            case R.id.button6:
                result = 6;
                break;
            case R.id.button7:
                result = 7;
                break;
            case R.id.button8:
                result = 8;
                break;
            case R.id.button9:
                result = 9;
                break;
        }
        return result;
    }

    private int getButtonFromNumber(int num) {
        int result = -1;
        switch (num) {
            case 1:
                result = R.id.button1;
                break;
            case 2:
                result = R.id.button2;
                break;
            case 3:
                result = R.id.button3;
                break;
            case 4:
                result = R.id.button4;
                break;
            case 5:
                result = R.id.button5;
                break;
            case 6:
                result = R.id.button6;
                break;
            case 7:
                result = R.id.button7;
                break;
            case 8:
                result = R.id.button8;
                break;
            case 9:
                result = R.id.button9;
                break;
        }
        return result;
    }

    private void displaySequenceOnTiles(int index) {
        Button button = (Button) findViewById(getButtonFromNumber(sequence[index]));
        button.setBackgroundColor(Color.BLUE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setBackgroundColor(Color.WHITE);
                if (index != size - 1) {
                    if (index + 1 < size - 1 && sequence[index+1] == sequence[index]) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                displaySequenceOnTiles(index+1);
                            }
                        }, 300);
                    } else {
                        displaySequenceOnTiles(index+1);
                    }
                }
            }
        }, 1000);
    }

    private void enableDisableAllButtons(int state) {
        for (int i = 1; i < 10; ++i) {
            Button button = (Button) findViewById(getButtonFromNumber(i));
            if (state == 0) {
                button.setClickable(false);
            } else {
                button.setClickable(true);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateSequence();
        //displaySequence();
    }

    public void startGame(View view) {
        Button button = (Button) findViewById(R.id.startButton);
        button.setVisibility(View.INVISIBLE);
        showScore();
        enableDisableAllButtons(0);
        displaySequenceOnTiles(0);
        enableDisableAllButtons(1);
    }

    public void showScore() {
        TextView pointLabel = (TextView) findViewById(R.id.pointLabel);
        pointLabel.setText(String.valueOf(score));
    }

    public void activateEndOfGamePopup(View view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.end_game_screen, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public void checkSequence(View view) {
        int action = getNumberFromButton(view.getId());
        if (action != sequence[current]) {
            // wrong answer
            Log.d("ACTION", "Activating end of game popup");
            activateEndOfGamePopup(view);
        }
        else {
            current++;
        }

        if (current == size) {
            addToSequence();
            //displaySequence();
            score++;
            showScore();
            current = 0;
            enableDisableAllButtons(0);
            displaySequenceOnTiles(0);
            enableDisableAllButtons(1);
        }
    }

    public void displaySequence() {
        String s = "";
        for (int i = 0; i < size; i++) {
            s += String.valueOf(sequence[i]);
        }
        EditText simpleEditText = (EditText) findViewById(R.id.textBox);
        simpleEditText.setText(s, TextView.BufferType.EDITABLE);
    }
}