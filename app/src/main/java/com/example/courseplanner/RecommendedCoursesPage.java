package com.example.courseplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.util.Log;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;


public class RecommendedCoursesPage extends AppCompatActivity {

    public ArrayList<Item> allClasses;
    public ArrayList<String> availableClasses;
    public ArrayList<String> passedClasses;
    public ListView recommendedView;
    public ArrayAdapter<String> recommendedCourses;
    public ArrayAdapter<String> prereqAdapter;

    public String sql;

    //function to check if an array contains a specified value
    public static boolean contains(ArrayList<String> array, String v) {

        //search array
        for (String i : array) {
            if (i.equals(v)) {
                return true;
            }
        }
        return false;
    }

    //function that searches as item array by id and returns the found item or null
    public static Item findItemById(ArrayList<Item> array, int id) {
        for(Item i : array) {
            if(i.id == id) {
                return i;
            }
        }
        return null;
    }

    public static boolean checkPrereq(ArrayList<Item> allClasses, ArrayList<String> passedClasses, int id) {

        if(id == 0) {
            return true;
        }

        Item toBeChecked = findItemById(allClasses, id);

        if(contains(passedClasses, toBeChecked.makeCourseLine())) { //check if it has a prereq
            return true;
        }
        else {
            return false;
        }

        //return false;
    }

    public static Item findRootPrereq(ArrayList<Item> allClasses, ArrayList<String> passedClasses, Item first, boolean firstIteration){

        if(first.prereqId == 0 && !firstIteration){
            return first;
        }
        else if(first.prereqId == 0) {
            return null;
        }

        Item prereq = findItemById(allClasses, first.prereqId);

        if(!contains(passedClasses, prereq.makeCourseLine())){
            return prereq;
        }
        else {
            return findRootPrereq(allClasses, passedClasses, prereq, false);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_courses_page);

        //array containing all courses as Items, populated by db query
        allClasses = new ArrayList<Item>();



        //array of classes that have already been taken, populated by intent
        passedClasses = new ArrayList<String>();

        //classes that can currently be taken
        availableClasses = new ArrayList<String>();

        //adapters for piping to ui
        recommendedCourses = new ArrayAdapter<String>(this, R.layout.class_list, R.id.classRow, availableClasses);


        recommendedView = findViewById(R.id.RecommendedCourses);


        //set adapters
        recommendedView.setAdapter(recommendedCourses);


        // get all classes expects an array of all classes with prereq
        //i.e. a union between the classes and prereq table
        ExecuteSQL getAllClasses;
        sql = "select * from course\n" +
                "LEFT JOIN Prerequisite ON Prerequisite.CourseID = Course.CourseID";

        //Execute Query
        ExecuteSQL getCourses = new ExecuteSQL(sql, 6); //Require 5 columns for data to be successfully pulled from db
        getCourses.execute();

        //Captures database response
        allClasses.addAll(getCourses.getDbResponse());


        //get passed classes from intent
        passedClasses = getIntent().getStringArrayListExtra("Selected_Class");

        for(String i : passedClasses) {
            Log.e("passed class: ", i);
        }


        for (Item course : allClasses) { //for each Course

            if (!contains(passedClasses, course.makeCourseLine())) //check if Course has NOT been passed
            {
                if(checkPrereq(allClasses, passedClasses, course.prereqId)){//check if prereq has been passed
                    recommendedCourses.add(course.makeCourseLine());
                }

            }

        }


        recommendedCourses.notifyDataSetChanged();


        Button yourButtonReturn = (Button) findViewById(R.id.btnReturnToHomepage);
        yourButtonReturn.setOnClickListener(new View.OnClickListener() {

            public void onClick(android.view.View v) {
                startActivity(new Intent(RecommendedCoursesPage.this, MainActivity.class));
            }
        });
    }
}
