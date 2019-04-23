package com.example.courseplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class Item {

    public int id;
    public String subject;
    public String cName;
    public String desc;
    public String dept;
    public int PID;
    public String prereqId;
    public boolean isTaken = false;
    public Boolean hasPrereq = false;
    public Boolean isPrereqTakehn = false;
    public String email;
    public String passWord;
    public String sectionCRN;
    public String timeStart;
    public String timeEnd;
    public String sessionDays;
    public String courseName;
    public String courseSubject;


    public Item(String uEmail, String pass) {
        email = uEmail;
        passWord = pass;
    }

    public Item(int id1, String courseName1, String courseSubject1, String courseDesc1, String courseDept1) {
        id = id1;
        cName = courseName1;
        subject = courseSubject1;
        desc = courseDesc1;
        dept = courseDept1;
    }


    public String makeCourseLine() {
        return subject + " - " + cName;
    }


    public Item(String crn, String start, String end, String days, String name, String subject) {

        sectionCRN = crn;
        timeStart = start;
        timeEnd = end;
        sessionDays = days;
        courseName = name;
        courseSubject = subject;

    }

    public String getSectionInfo() {
        return
                sectionCRN + " - " +
                        timeStart + " - " +
                        timeEnd + " - " +
                        sessionDays + " - " +
                        courseName + " - " +
                        courseSubject;
    }
}

