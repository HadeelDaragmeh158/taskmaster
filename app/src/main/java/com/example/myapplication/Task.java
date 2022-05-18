package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private String title , body , state ;
    private List<String> stateContentList  = new ArrayList<>();

    public Task(String title, String body, String state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private  void addContentList (){
        this.stateContentList.add("new");
        this.stateContentList.add("assigned");
        this.stateContentList.add("progress");
        this.stateContentList.add("complete");
    }



}
