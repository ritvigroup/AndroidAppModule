package com.ritvi.kaajneeti.pojo.event;

/**
 * Created by sunil on 21-03-2018.
 */

public class EventAttachment {
    String name;
    String type;
    String file_path;
    String thumb_path;
    boolean is_demo;

    public EventAttachment(String name,String type, String file_path, String thumb_path, boolean is_demo) {
        this.name = name;
        this.type=type;
        this.file_path = file_path;
        this.thumb_path = thumb_path;
        this.is_demo = is_demo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getThumb_path() {
        return thumb_path;
    }

    public void setThumb_path(String thumb_path) {
        this.thumb_path = thumb_path;
    }

    public boolean isIs_demo() {
        return is_demo;
    }

    public void setIs_demo(boolean is_demo) {
        this.is_demo = is_demo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EventAttachment{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", file_path='" + file_path + '\'' +
                ", thumb_path='" + thumb_path + '\'' +
                ", is_demo=" + is_demo +
                '}';
    }
}
