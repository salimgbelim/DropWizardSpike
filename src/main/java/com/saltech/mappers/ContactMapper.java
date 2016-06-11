package com.saltech.mappers;

import com.saltech.representations.Contact;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactMapper implements ResultSetMapper<Contact>{

    @Override
    public Contact map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {

        Contact contact = new Contact(
                resultSet.getInt("id"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("phone"));

        return contact;
    }
}
