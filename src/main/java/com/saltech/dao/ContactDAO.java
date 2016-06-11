package com.saltech.dao;

import com.saltech.mappers.ContactMapper;
import com.saltech.representations.Contact;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface ContactDAO {

    @Mapper(ContactMapper.class)
    @SqlQuery("select * from phonebook.contact where id = :id")
    Contact getContactById(@Bind("id") int id);


    @GetGeneratedKeys
    @SqlUpdate("insert into phonebook.contact(id, firstName, lastName, phone) values (NULL, :firstName, :lastName, :phone)")
    int createContact(@Bind("firstName") String firstName,
                      @Bind("lastName") String lastName,
                      @Bind("phone") String phone);

    @SqlUpdate("update phonebook.contact set firstName = :firstName, lastName = :lastName, phone = :phone where id = :id")
    void updateContact(@Bind("id") int id,
                       @Bind("firstName") String firstName,
                       @Bind("lastName") String lastName,
                       @Bind("phone") String phone);

    @SqlUpdate("delete from phonebook.contact where id = :id")
    void deleteContact(@Bind("id") int id);
}
