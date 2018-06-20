package com.pakhigupta.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    Button button;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void resetTimer() {

        textView.setText("00:30");
        seekBar.setProgress(30);
        countDownTimer.cancel();
        button.setText("Go!");
        seekBar.setEnabled(true);
        counterIsActive = false;

    }

    public void updateTimer(int secondsLeft) {

        int minutes = (int) secondsLeft/60;
        int seconds = secondsLeft - minutes*60;

        String second = Integer.toString(seconds);
        if(second.length() == 1) {
            second = "0" + second;
        }
        String minute = Integer.toString(minutes);
        if(minute.length() == 1) {
            minute = "0" + minute;
        }
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(minute + ":"+ second);

    }

    public void controlTimer(View view) {

        if(counterIsActive == false) {

            counterIsActive = true;
            seekBar.setEnabled(false);
            button.setText("Stop!");

            final TextView textView = (TextView) findViewById(R.id.textView);
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {

                    updateTimer((int) (l / 1000));
                }

                @Override
                public void onFinish() {
                    //Log.i("Info","Timer ends");
                    textView.setText("00:00");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    resetTimer();

                }
            }.start();
        }
        else {

            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        button = (Button) findViewById(R.id.button);
        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
