package com.hoka.ormlitehoka;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    //private DatabaseHelper dbHelper;

    private DatabaseHelp dh;
    private StudentsDao studentsDao;
    private DisciplineDao disciplineDao;
    private List<Students> students;
    private Students s;
    private int firstId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //doNoteDataStuff();

        dh = new DatabaseHelp(MainActivity.this);

        students = new ArrayList<Students>();

        s = new Students();
        s.setName("Vinicius Thiengo");
        s.setDisciplines(Arrays.asList(new Discipline("Math", "MT"), new Discipline("History", "HI")));
        students.add(s);

        s = new Students();
        s.setName("Jones ");
        s.setDisciplines(Arrays.asList(new Discipline("Geo", "GE"), new Discipline("Arts", "Ar")));
        students.add(s);



        try {
            studentsDao = new StudentsDao(dh.getConnectionSource());
            disciplineDao = new DisciplineDao(dh.getConnectionSource());

            //CREATE
            for (Students student : students) {
                int result = studentsDao.create(student);

                if (result == 1) {
                    for (Discipline d : student.getDisciplines()) {
                        d.setStudent(student);
                        disciplineDao.create(d);
                    }
                    firstId = firstId == 0 ? student.getId() : firstId;
                }
            }
            //GET ALL LINES
            Log.d("Script", " ");
            Log.d("Script", "GET ALL LINES");
            students = studentsDao.queryForAll();
            for (Students student : students) {
                Log.d("Script", "Name: " + student.getName() + "\nID" + student.getId() + "\nDisciplines: "
                                                + student.getDisciplines().size());
                    for (Discipline d : student.getDisciplines()) {
                        Log.d("Script", "Discipline: " + d.getName() + "\nID" + d.getId() + "\nCode: "
                                + d.getCode());
                    }
                Discipline d = new Discipline("Portugues", "PT");
                d.setStudent(student);
                disciplineDao.create(d);

                student.setName(student.getName() + " - ANDROID CLASS");
                studentsDao.update(student);
            }

            //GET ALL LINES AGAIN
            Log.d("Script", " ");
            Log.d("Script", "GET ALL LINES AGAIN");
            students = studentsDao.queryForAll();
            for (Students student : students) {
                Log.d("Script", "Name: " + student.getName() + "\nID" + student.getId() + "\nDisciplines: "
                        + student.getDisciplines().size());
                for (Discipline d : student.getDisciplines()) {
                    Log.d("Script", "Discipline: " + d.getName() + "\nID" + d.getId() + "\nCode: "
                            + d.getCode());
                }
            }

            //GET SPECIFIC LINE BY ID
            Log.d("Script", " ");
            Log.d("Script", "GET SPECIFIC LINE BY ID");
            s = studentsDao.queryForId(firstId);
            Log.d("Script", "Name: " + s.getName() + "\nID" + s.getId() + "\nDisciplines: "
                    + s.getDisciplines().size());
            for (Discipline d : s.getDisciplines()) {
                Log.d("Script", "Discipline: " + d.getName() + "\nID" + d.getId() + "\nCode: "
                        + d.getCode());
            }
            disciplineDao.delete(s.getDisciplines());


            //GET SPECIFIC LINE BY NAME
            Log.d("Script", " ");
            Log.d("Script", "GET SPECIFIC LINE BY NAME");
            Map<String, Object> values = new HashMap<String, Object>();
            values.put("name", "Vinicius Thiengo - ANDROID CLASS");
            students = studentsDao.queryForFieldValues(values);
            for (Students student : students) {
                Log.d("Script", "Name: " + student.getName() + "\nID" + student.getId() + "\nDisciplines: "
                        + student.getDisciplines().size());
                for (Discipline d : student.getDisciplines()) {
                    Log.d("Script", "Discipline: " + d.getName() + "\nID" + d.getId() + "\nCode: "
                            + d.getCode());
                    disciplineDao.delete(d);
                }
            }

            //GET ALL LINES AGAIN BY RAW
            Log.d("Script", " ");
            Log.d("Script", "GET ALL LINES AGAIN BY RAW");
            GenericRawResults<Students> raw = studentsDao.queryRaw("SELECT id, name FROM student WHERE LIKE \"Vinicius%\"", new RawRowMapper<Students>() {
                @Override
                public Students mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
                    return new Students(Integer.parseInt(resultColumns[0]), resultColumns[1]);
                }
            });
            for (Students student : raw) {
                Log.d("Script", "Name: " + student.getName() + "\nID" + student.getId());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dh.close();
    }

    /*

    private void doNoteDataStuff() {
        dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);

        RuntimeExceptionDao<Note, Integer> noteDao = dbHelper.getNoteRuntimeDao();
        //create
        noteDao.create(new Note("Note1", "Note 1 text"));
        noteDao.create(new Note("Note2", "Note 2 text"));
        noteDao.create(new Note("Note3", "Note 3 text"));

        //query
        List<Note> notes = noteDao.queryForAll();
        Log.d("demo", notes.toString());

        notes = noteDao.queryForEq("id", 1);
        Log.d("demo", notes.toString());

        noteDao.delete(noteDao.queryForAll());

        notes = noteDao.queryForAll();
        Log.d("demo", notes.toString());

    }*/
}
