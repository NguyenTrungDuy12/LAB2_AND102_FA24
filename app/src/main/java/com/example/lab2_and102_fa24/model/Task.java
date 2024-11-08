package com.example.lab2_and102_fa24.model;

public class Task {
    private int id, status;
    private String title, content, date, type;


    // mục đích của constructor này là gì?
// deo biết, nãy có cái string vừa lỗi bên dao, tao baasm alt enter chắc nó nhảy ra
    // xoá bỏ đi xong vào làm lại bên taskDao nữa
    //lm nhu nao xoá đi rồi nó báo lỗi ở đâu thì sửa ở đấy
    public Task(int id, String title, String content, String date, String type, int status) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.content = content;
        this.date = date;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", status=" + status +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
