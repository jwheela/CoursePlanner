package com.example.courseplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class CompletedCoursesPage extends AppCompatActivity {

    //Declare variables
    private ArrayList<String> classList;
    public ArrayList<String> selectedClass;
    public ArrayList<Item> classListObj;
    private ArrayAdapter<String> classAdapter;
    public Button next;
    public ListView classListView;
    public String sql;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_courses_page);

        //Starts the array lists
        classList = new ArrayList<String>();
        selectedClass = new ArrayList<String>();
        classListObj = new ArrayList<Item>(); //Receives the objects from ExecuteSQL

        //Start the Array adapter
        classAdapter = new ArrayAdapter<String>(this, R.layout.class_list, R.id.classRow, classList);

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

        //Populates the classList
        for(Item i:classListObj){
            classList.add(i.makeCourseLine());
        }

        //Notifies adapter of any changes
        classAdapter.notifyDataSetChanged();


        //Capturing Selected Courses


        //Onclick listener for each individual line in courseList
        classListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //Saving clicked item into a variable
                String selectedItem = ((TextView)view).getText().toString();

                //Checking if selected item is in selected list

                //If yes removing from the list(equivalent to unchecking)
                if(selectedClass.contains(selectedItem)){
                    selectedClass.remove(selectedItem);

                    //If no adding to the list(equivalent to checking)
                } else {
                    selectedClass.add(selectedItem);
                }
            }
        });

    }
}
