package com.example.lab2_and102_fa24.model;

public class ToDo {
    public int Id;
    public String Title;
    public String Content;
    public String  Date;
    public String Type;
    public int Status;

    public ToDo() {
    }

    public ToDo(String title, String content, String date, String type) {
        Title = title;
        Content = content;
        Date = date;
        Type = type;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
