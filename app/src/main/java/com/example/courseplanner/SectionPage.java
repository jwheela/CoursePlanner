package com.example.courseplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SectionPage extends AppCompatActivity {

    private ArrayList<String> sectionList;
    public ArrayList<String> selectedSection;
    public ArrayList<Item> sectionListObj;
    public String sql;
    public String courseID;
    public ListView sectionListView;
    public ArrayAdapter<String> dataAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_page);

        sectionList = new ArrayList<String>();
        selectedSection = new ArrayList<String>();
        sectionListObj = new ArrayList<Item>();

        dataAdapter = new ArrayAdapter<String>(this, R.layout.activity_completed_courses_page, sectionList);

        sectionListView = findViewById(R.id.sectionList);
        sectionListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        sectionListView.setAdapter(dataAdapter);

        Intent courseToFetch = getIntent();

        courseID = courseToFetch.getStringExtra("id"); //Needs to call the correct key

        sql = "SELECT Section.SectionCRN, Section.TimeStart, Section.TimeEnd, Section.SessionDays, Course.CourseName, Course.CourseSubject " +
                "FROM Section, Course WHERE Course.CourseID = " + courseID
                + "AND Section.CourseID = " + courseID + ";";

        ExecuteSQL dbController = new ExecuteSQL(sql, 3);
        dbController.execute();

        sectionListObj.addAll(dbController.getDbResponse());

        for(Item i:sectionListObj){
            sectionList.add(i.makeCourseLine());
        }

        dataAdapter.notifyDataSetChanged();

        sectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //Saving clicked item into a variable
                String selectedItem = ((TextView)view).getText().toString();

                //Will pass page to section info eventually

            }
        });



    }

}
