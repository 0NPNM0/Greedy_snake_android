package com.example.project_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class SelectPlaces extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_places);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button mainland = (Button) findViewById(R.id.button_mainland);
        Button macao = (Button) findViewById(R.id.button_macao);
        Button hongkong = (Button) findViewById(R.id.button_hongkong);
        Button taiwan = (Button) findViewById(R.id.button_taiwan);


        taiwan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectPlaces.this,GameGround.class);
                intent.putExtra(GameGround.BG,"1");
                startActivity(intent);
            }
        });

        mainland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectPlaces.this,GameGround.class);
                intent.putExtra(GameGround.BG,"2");
                startActivity(intent);
            }
        });

        macao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectPlaces.this,GameGround.class);
                intent.putExtra(GameGround.BG,"3");
                startActivity(intent);
            }
        });

       hongkong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectPlaces.this,GameGround.class);
                intent.putExtra(GameGround.BG,"4");
                startActivity(intent);
            }
        });
    }
}