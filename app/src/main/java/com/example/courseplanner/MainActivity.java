package com.example.courseplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button yourButton = (Button) findViewById(R.id.btnComp);
        yourButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(android.view.View v) {
                startActivity(new Intent(MainActivity.this, CompletedCoursesPage.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        Button yourButton2 = (Button) findViewById(R.id.btnComp2);
        yourButton2.setOnClickListener(new View.OnClickListener() {

            public void onClick(android.view.View v) {
                //Creating intent
                Intent intent = new Intent(getApplicationContext(), RecommendedCoursesPage.class);

                //Attaching selected classes and selected program to be passed on to next activity
                intent.putExtra("Selected_Class", new ArrayList<String>());


                //Redirecting to the next activity
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });


        Button yourButton3 = (Button) findViewById(R.id.btnComp3);
        yourButton3.setOnClickListener(new View.OnClickListener() {

            public void onClick(android.view.View v) {
                startActivity(new Intent(MainActivity.this, ProfilePage.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

    }

}




