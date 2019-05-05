package com.example.courseplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class CompletedCoursesPage extends AppCompatActivity {

    //Declare variables
    private ArrayList<String> classList;
    public ArrayList<String> selectedClass;
    public ArrayList<Item> classListObj;
    private ArrayAdapter<String> classAdapter;
    public Button next;
    public ListView classListView;
    public String sql;

    public ArrayList<String> getCompletedClassesFromPrefs() {

        //setup shared pref
        SharedPreferences prefs = getBaseContext().getSharedPreferences("User", 0);

        //grab the string with comma seperated course strings
        String superString = prefs.getString("CompletedCourse", "");

        //split the superstring on commas and load that into arraylist
        return new ArrayList<>(Arrays.asList(superString.split(",")));
    }

    public void setCompletedClassesInPrefs(ArrayList<String> array) {

        //setup editor
        SharedPreferences prefs = getBaseContext().getSharedPreferences("User", 0);
        SharedPreferences.Editor editor = prefs.edit();

        //turn arraylist into csv string
        String output = "";
        for (String str : array) {
            output += str + ",";
        }

        //push csv string to prefs
        editor.putString("CompletedCourse", output);
        editor.commit();
    }

    public void updateChecks(ArrayList<String> classes, ListView listView) {

        //get children of list view
        int children = listView.getChildCount();
        Log.e(String.valueOf(children), "updateChecks got called!");

        //for each child check if it is in completed classes
        for(int i = 0; i < children; i++) {

            View v = listView.getChildAt(i);

            //if the child is completed check its check box
            if(classes.contains(((TextView)v).getText().toString())){
                ((CheckedTextView)v).setChecked(true);
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_courses_page);

        //Starts the array lists
        classList = new ArrayList<String>();
        selectedClass = getCompletedClassesFromPrefs();
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
        updateChecks(selectedClass, classListView);


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



        //Next button functionality
        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setCompletedClassesInPrefs(selectedClass);

                //Creating intent
                Intent intent = new Intent(getApplicationContext(), RecommendedCoursesPage.class);

                //Attaching selected classes and selected program to be passed on to next activity
                intent.putExtra("Selected_Class", selectedClass);


                //Redirecting to the next activity
                startActivity(intent);

            }

        });
    }
}
