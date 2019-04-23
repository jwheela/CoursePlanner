package com.example.courseplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class RecommendedCoursesPage extends AppCompatActivity {

    public ArrayList<Item> allClasses;
    public ArrayList<String> availableClasses;
    public ArrayList <String> passedClasses;
    public ListView recommendedView;
    public ArrayAdapter<String> recommendedCourses;

    public String sql;

    //function to check if an array contains a specified value
    public static boolean contains(ArrayList<String> array, String v) {

        boolean result = false;

        //from stack overflow
        for (String i : array) {
            if (i == v) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_courses_page);

        allClasses = new ArrayList<Item>();
        passedClasses = new ArrayList<String>();
        availableClasses = new ArrayList<String>();

        recommendedCourses = new ArrayAdapter <String>(this, R.layout.class_list, R.id.classRow, availableClasses);

        recommendedView = findViewById(R.id.RecommendedCourses);


        // get all classes expects an array of all classes with prereq
        //i.e. a union between the classes and prereq table
        ExecuteSQL getAllClasses;

        //getPassed classes expects an array of passed class ids
        passedClasses = getIntent().getStringArrayListExtra("Selected_Class");

        sql = "SELECT * FROM Course";

        //Execute Query
        ExecuteSQL getCourses = new ExecuteSQL(sql, 5); //Require 5 columns for data to be successfully pulled from db
        getCourses.execute();

        //Captures database response
        allClasses.addAll(getCourses.getDbResponse());

        for (Item i : allClasses) { //for each Course

            if (!contains(passedClasses, i.makeCourseLine())) //check if Course has not been passed
            {
                if (i.prereqId != null) { //check if those Courses have a prereq

                    if (contains(passedClasses, i.makeCourseLine())) { //check if prereq is passed

                        //if class not already passed and prereqs passed class is available
                        availableClasses.add(i.makeCourseLine());
                    }
                } else {
                    //if not already passed and no prereq class is available
                    availableClasses.add(i.makeCourseLine());
                }
            }
        }
        recommendedView.setAdapter(recommendedCourses);

        recommendedCourses.notifyDataSetChanged();




    }
}

