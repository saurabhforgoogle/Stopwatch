package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    int currentTime=0;
    int Hours=0;
    int minutes=0;
    int seconds=0;
    int milliseconds=0;
    int incrTimeeachstep=30;

    boolean playinPlayPause=false;
    boolean darkMode=false;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton reset=findViewById(R.id.reset);
        ImageButton pauseplay=findViewById(R.id.pauseplay);
        ImageButton play=findViewById(R.id.start);
        ImageButton darkbutton=findViewById(R.id.darkmode);
        reset.setVisibility(View.INVISIBLE);
        pauseplay.setVisibility(View.INVISIBLE);
        play.setOnClickListener(startClockAction);
        reset.setOnClickListener(resetClockAction);
        pauseplay.setOnClickListener(pauseplayClockAction);
        darkbutton.setOnClickListener(changeDarkMode);


    }

    private View.OnClickListener changeDarkMode=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            darkMode=!darkMode;
            ConstraintLayout layout=findViewById(R.id.mainlayout);
            ImageButton dark=findViewById(R.id.darkmode);
            ImageButton reset=findViewById(R.id.reset);
            ImageButton play=findViewById(R.id.start);
            ImageButton pauseplay=findViewById(R.id.pauseplay);
            TextView stopwatch=findViewById(R.id.stopwatch);
            if(darkMode){
                layout.setBackgroundResource(R.color.black);
                dark.setImageResource(R.drawable.ic_baseline_wb_sunny_24_dark);
                dark.setBackgroundResource(R.color.black);
                reset.setImageResource(R.drawable.ic_baseline_stop_circle_24_dark);
                reset.setBackgroundResource(R.color.black);
                play.setImageResource(R.drawable.ic_baseline_play_circle_filled_24_dark);
                play.setBackgroundResource(R.color.black);
                stopwatch.setTextColor(Color.parseColor("#FFFFFF"));
                stopwatch.setBackgroundResource(R.color.black);
                if(playinPlayPause){
                    pauseplay.setImageResource(R.drawable.ic_baseline_play_circle_filled_24_dark);
                    pauseplay.setBackgroundResource(R.color.black);
                }
                else{
                    pauseplay.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24_dark);
                    pauseplay.setBackgroundResource(R.color.black);
                }
            }
            else{
                layout.setBackgroundResource(R.color.white);
                dark.setImageResource(R.drawable.ic_baseline_wb_sunny_24);
                dark.setBackgroundResource(R.color.white);
                reset.setImageResource(R.drawable.ic_baseline_stop_circle_24);
                reset.setBackgroundResource(R.color.white);
                play.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                play.setBackgroundResource(R.color.white);
                stopwatch.setTextColor(Color.parseColor("#FF000000"));
                stopwatch.setBackgroundResource(R.color.white);
                if(playinPlayPause){
                    pauseplay.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                    pauseplay.setBackgroundResource(R.color.white);
                }
                else{
                    pauseplay.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                    pauseplay.setBackgroundResource(R.color.white);
                }
            }
        }
    };

    private View.OnClickListener startClockAction=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageButton reset=findViewById(R.id.reset);
            ImageButton pauseplay=findViewById(R.id.pauseplay);
            ImageButton play=findViewById(R.id.start);
            reset.setVisibility(View.VISIBLE);
            pauseplay.setVisibility(View.VISIBLE);
            if(darkMode){
                pauseplay.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24_dark);
            }
            else {
                pauseplay.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
            }
            play.setVisibility(View.INVISIBLE);
            Clock("start");
        }
    };

    private View.OnClickListener resetClockAction=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageButton reset=findViewById(R.id.reset);
            ImageButton pauseplay=findViewById(R.id.pauseplay);
            ImageButton play=findViewById(R.id.start);
            reset.setVisibility(View.INVISIBLE);
            pauseplay.setVisibility(View.INVISIBLE);
            playinPlayPause=false;
            play.setVisibility(View.VISIBLE);
            Clock("reset");
        }
    };

    private View.OnClickListener pauseplayClockAction=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageButton pauseplay=findViewById(R.id.pauseplay);
            if(playinPlayPause){
                if(darkMode){
                    pauseplay.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24_dark);
                }
                else{
                    pauseplay.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                }

                playinPlayPause=false;
                Clock("start");
            }
            else{
                if(darkMode){
                    pauseplay.setImageResource(R.drawable.ic_baseline_play_circle_filled_24_dark);
                }
                else{
                    pauseplay.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                }

                playinPlayPause=true;
                Clock("pause");
            }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            currentTime+=incrTimeeachstep;
            TextView stopwatch=findViewById(R.id.stopwatch);
            int temp=currentTime;
            Hours=temp/3600000;//returns quotient
            temp=temp%3600000;
            minutes=temp/60000;
            temp=temp%60000;
            seconds=temp/1000;
            temp=temp%1000;
            milliseconds=temp/10;
            if(Hours==0){
                stopwatch.setText(String.format("%02d", minutes)+":"+String.format("%02d", seconds)+"."+String.format("%02d", milliseconds));
                //Build padding upto two digit on left
                String.format("%02d", minutes);
            }
            else{
                stopwatch.setText(String.format("%02d", Hours)+":"+String.format("%02d", minutes)+":"+String.format("%02d", seconds)+"."+String.format("%02d", milliseconds));
                stopwatch.setTextSize(45);
            }
            Clock("start");
        }
    };


    private void Clock(String action) {
        if(action.equals("start")){
            handler.postDelayed(runnable, incrTimeeachstep);
        }
        else if(action.equals("pause")){
            handler.removeCallbacks(runnable);
        }
        else if(action.equals("reset")){
            handler.removeCallbacks(runnable);
            currentTime=0;
            TextView stopwatch=findViewById(R.id.stopwatch);
            stopwatch.setText("00:00.00");
            Hours=0;
            minutes=0;
            seconds=0;
            milliseconds=0;
            stopwatch.setTextSize(55);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

}