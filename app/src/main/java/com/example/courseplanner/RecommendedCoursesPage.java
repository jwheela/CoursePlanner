package com.example.courseplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class RecommendedCoursesPage extends AppCompatActivity {

    private ArrayList<String> classList;
    public ArrayList<String> prereqList;
    public ArrayList<Item> classListObj;

    private ArrayAdapter<String> classAdapter;
    private ArrayAdapter<String> prereqAdapter;

    public Button next;
    public ListView classListView;
    public String sql;
    public String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_courses_page);

        Intent courseToFetch = getIntent();

        courseID = courseToFetch.getStringExtra("id"); //Needs to call the correct key

        //Starts the array lists
        classList = new ArrayList<String>();
        prereqList = new ArrayList<String>();
        classListObj = new ArrayList<Item>(); //Receives the objects from ExecuteSQL

        //Start the Array adapter
        classAdapter = new ArrayAdapter<String>(this, R.layout.activity_completed_courses_page, classList);
        prereqAdapter = new ArrayAdapter<String>(this, R.layout.activity_completed_courses_page, prereqList);

        //Initializing list view and assigning array adapter to it
        classListView = (ListView) findViewById(R.id.classList);
        classListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        classListView.setAdapter(classAdapter);

        //Populating ListView with all classes

        //Query to pull all classes form database
        sql = "SELECT * FROM Course";

        //Execute Query
        ExecuteSQL getCourses = new ExecuteSQL(sql, 5); //Require 5 columns for data to be successfully pulled from db
        getCourses.execute();

        //Captures database response
        classListObj.addAll(getCourses.getDbResponse());



    }
}