package com.example.helphero;

public class TaskModel {
    private String taskTitle;
    private int id;

    public TaskModel(int Id, String taskTitle){
        this.id = Id;
        this.taskTitle = taskTitle;
    }

    public TaskModel(){

    }
    public int getTaskId() {
        return id;
    }

    public void setTaskId(int taskId) {
        this.id = taskId;
    }

    public TaskModel( String taskTitle){

        this.taskTitle = taskTitle;
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
