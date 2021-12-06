package com.example.helphero;

public class TaskModel {
    private String taskTitle;
    private int id;
    private String username;

    public TaskModel(int Id, String taskTitle){
        this.id = Id;
        this.taskTitle = taskTitle;
        username = "";
    }

    public TaskModel(){

    }
    public int getTaskId() {
        return id;
    }

    public void setTaskCreator(String username) {

        this.username = username;
    }

    public String getTaskCreator() {
        return username;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    @Override
    public String toString() {
        return taskTitle;

    }
}
