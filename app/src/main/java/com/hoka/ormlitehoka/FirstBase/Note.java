package com.hoka.ormlitehoka.FirstBase;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class Note {
    @DatabaseField(generatedId = true)
    int id;
    @DatabaseField(dataType = DataType.STRING)
    String subject;
    @DatabaseField(dataType = DataType.STRING)
    String text;
    @DatabaseField(dataType = DataType.DATE)
    Date date;

    public Note() {
        //Constructor
    }

    public Note(String subject, String text) {
        this.subject = subject;
        this.text = text;
        this.date = new Date(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }




}
