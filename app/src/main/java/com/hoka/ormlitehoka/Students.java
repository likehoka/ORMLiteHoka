package com.hoka.ormlitehoka;

/**
 * Created by hoka on 13.03.2017.
 */

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

@DatabaseTable(tableName = "student")
public class Students {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(dataType = DataType.STRING)
    private String name;

    @ForeignCollectionField
    private Collection<Discipline> disciplines;

    public Students() {
    }

    public Students(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(Collection<Discipline> disciplines) {
        this.disciplines = disciplines;
    }
}
