package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class SetTypeActivity extends AppCompatActivity {

    CheckBox entertainment;
    CheckBox military;
    CheckBox finance;
    CheckBox sport;
    CheckBox technology;
    CheckBox car;
    CheckBox education;
    CheckBox culture;
    CheckBox health;
    CheckBox society;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.bottom_in,R.anim.bottom_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_type);

        entertainment = findViewById(R.id.entertainment_checkbox);
        military = findViewById(R.id.military_checkbox);
        finance = findViewById(R.id.finance_checkbox);
        sport = findViewById(R.id.sport_checkbox);
        technology = findViewById(R.id.technology_checkbox);
        car = findViewById(R.id.car_checkbox);
        education = findViewById(R.id.education_checkbox);
        culture = findViewById(R.id.culture_checkbox);
        health = findViewById(R.id.health_checkbox);
        society = findViewById(R.id.society_checkbox);

        Intent intent = getIntent();
        boolean entertainmentchecked = intent.getBooleanExtra("娱乐", true);
        boolean militarychecked = intent.getBooleanExtra("军事",true);
        boolean financechecked = intent.getBooleanExtra("财经",true);
        boolean sportchecked = intent.getBooleanExtra("体育",true);
        boolean technologychecked = intent.getBooleanExtra("科技",true);
        boolean carchecked = intent.getBooleanExtra("汽车",true);
        boolean educationchecked = intent.getBooleanExtra("教育",true);
        boolean culturechecked = intent.getBooleanExtra("文化",true);
        boolean healthchecked = intent.getBooleanExtra("健康",true);
        boolean societychecked = intent.getBooleanExtra("社会",true);

        entertainment.setChecked(entertainmentchecked);
        military.setChecked(militarychecked);
        finance.setChecked(financechecked);
        sport.setChecked(sportchecked);
        technology.setChecked(technologychecked);
        car.setChecked(carchecked);
        education.setChecked(educationchecked);
        culture.setChecked(culturechecked);
        health.setChecked(healthchecked);
        society.setChecked(societychecked);

        Button cancel = findViewById(R.id.set_type_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button confirm = findViewById(R.id.set_type_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_send = new Intent(SetTypeActivity.this, MainActivity.class);
                intent_send.putExtra("娱乐",entertainment.isChecked());
                intent_send.putExtra("军事",military.isChecked());
                intent_send.putExtra("财经",finance.isChecked());
                intent_send.putExtra("体育",sport.isChecked());
                intent_send.putExtra("科技",technology.isChecked());
                intent_send.putExtra("汽车",car.isChecked());
                intent_send.putExtra("教育",education.isChecked());
                intent_send.putExtra("文化",culture.isChecked());
                intent_send.putExtra("健康",health.isChecked());
                intent_send.putExtra("社会",society.isChecked());
                startActivity(intent_send);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_silent,R.anim.bottom_out);
    }
}