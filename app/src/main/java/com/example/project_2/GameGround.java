package com.example.project_2;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class GameGround extends AppCompatActivity implements GestureDetector.OnGestureListener, SnakeHandlerDelegate{

    public static final String BG = "1";
    private SnakeHandler snakeHandler = new SnakeHandler();
    private ItemType[] gameData = new ItemType[225];
    private ItemAdapter adapter;
    private GridView gridView;
    private int score;
    private int maxScore;
    private GestureDetector gestureDetector;
    private Timer timer;
    private static final int TAG = 1;
    private Handler handler;
    public String choose = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ground);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageView bg1 = (ImageView) findViewById(R.id.background1);
        ImageView bg2 = (ImageView) findViewById(R.id.background2);
        Intent intent = getIntent();
        String backg = intent.getStringExtra(BG);
        if(backg.equals("2")) {
            bg1.setImageResource(R.drawable.mainland);
            bg2.setImageResource(R.drawable.mainland);
            choose = "mainland";
        }else if(backg.equals("1")) {
            bg2.setImageResource(R.drawable.taiwan);
            bg1.setImageResource(R.drawable.taiwan);
            choose = "taiwan";
        }else if(backg.equals("4")) {
            bg2.setImageResource(R.drawable.hongkong);
            bg1.setImageResource(R.drawable.hongkong);
            choose = "hongkong";
        }else if(backg.equals("3")) {
            bg2.setImageResource(R.drawable.macao);
            bg1.setImageResource(R.drawable.macao);
            choose = "macao";
        }

        gestureDetector = new GestureDetector(this,this);

        snakeHandler.delegate = this;

        handler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                if(msg.what == TAG){
                    snakeHandler.go();
                    refresh();
                }
            }
        };

        initTimer();

        gridView = (GridView)findViewById(R.id.gridView);
        gridView.setEnabled(false);


        adapter = new ItemAdapter(GameGround.this,R.layout.item_view,gridView, Arrays.asList(gameData),choose);

        gridView.setAdapter(adapter);
        refresh();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        float startX = motionEvent.getX();
        float startY = motionEvent.getY();
        float endX = motionEvent1.getX();
        float endY = motionEvent1.getY();

        float deltaX = endX - startX;
        float deltaY = endY - startY;

        if(Math.abs(deltaY) > Math.abs(deltaX)) {
            if(deltaY > 0) {
                snakeHandler.setCurrentDirection(Direction.DOWN);
            } else {
                snakeHandler.setCurrentDirection(Direction.UP);
            }
        } else {
            if(deltaX > 0) {
                snakeHandler.setCurrentDirection(Direction.RIGHT);
            } else {
                snakeHandler.setCurrentDirection(Direction.LEFT);
            }
        }

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }



    public void refresh() {
        for(int i=0; i<15; i++) {
            for(int j=0; j<15; j++) {
                this.gameData[i*15 + j] = snakeHandler.getGameData()[i][j];
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void initTimer() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(TAG);
            }
        };
        if(choose.equals("mainland")){
            timer.schedule(task, 100, 100);
        }
        if(choose.equals("macao")){
            timer.schedule(task, 150, 150);
        }
        if(choose.equals("hongkong")){
            timer.schedule(task, 50, 50);
        }
        if(choose.equals("taiwan")){
            timer.schedule(task, 200, 200);
        }
    }

    @Override
    public void onGameOver() {
        timer.cancel();
        timer = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over!")
                .setMessage("You score is:"+ score +"\nThe best score is:"+maxScore+"\nWanna Try again？")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        snakeHandler.clean();
                        initTimer();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onScoreChanged(int s) {
        if(s > maxScore) maxScore = s;
        score = s;
    }

}