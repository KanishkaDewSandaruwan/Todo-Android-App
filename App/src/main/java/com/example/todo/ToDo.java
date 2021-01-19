package com.example.todo;

public class ToDo {
    private int id;
    private String title;

    private String desc,started;
    private long finished;

    public ToDo() {
    }

    public ToDo(int id, String title, String desc, String started, long finished) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.started = started;
        this.finished = finished;
    }

    public ToDo(String title, String desc, String started, long finished) {
        this.title = title;
        this.desc = desc;
        this.started = started;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

}
