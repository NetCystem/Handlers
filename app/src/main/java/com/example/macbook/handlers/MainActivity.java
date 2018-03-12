package com.example.macbook.handlers;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private ProgressBar pbCount;
    private CheckBox chbInfo;
    private TextView tvInfo;
    int count;
    final int max = 100;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        pbCount = findViewById(R.id.pbCount);
        pbCount.setMax(max);
        pbCount.setProgress(0);
        chbInfo = findViewById(R.id.chbInfo);
        tvInfo = findViewById(R.id.tvInfo);

        chbInfo.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    tvInfo.setVisibility(View.VISIBLE);
                    handler.post(showInfo);
                }else {
                    tvInfo.setVisibility(View.GONE);
                    handler.removeCallbacks(showInfo);
                }

            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                for (count = 1; count<max; count++){
                    try {
                        TimeUnit.MICROSECONDS.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(updateProgress);
                }

            }
        });
        thread.start();

    }


    //обновление Progressbar

    Runnable updateProgress = new Runnable() {
        @Override
        public void run() {
            pbCount.setProgress(count);
        }
    };


    //Показ информации

    Runnable showInfo = new Runnable() {
        @Override
        public void run() {
            tvInfo.setText("Count = " + count);
            //планирует сам себя через 10000 мсек
            handler.postDelayed(showInfo, 10000);
        }
    };


//    public MainActivity getMain(){
//        return this;
//    }



}
