package com.hoka.ormlitehoka;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by hoka on 13.03.2017.
 */

public class StudentsDao extends BaseDaoImpl<Students, Integer> {

    protected StudentsDao(ConnectionSource connectionSource) throws SQLException {
        super(Students.class);
        setConnectionSource(connectionSource);
        initialize();
    }
}
