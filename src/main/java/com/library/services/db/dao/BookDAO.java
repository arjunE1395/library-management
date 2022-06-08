package com.library.services.db.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import com.library.services.db.dto.Book;
import com.library.services.db.mapper.BookMapper;

import java.util.List;
import java.util.Optional;

@RegisterRowMapper(BookMapper.class)
public interface BookDAO {

    @SqlQuery("SELECT * FROM BOOKS WHERE STATUS = 1")
    List<Book> findAll();

    @SqlQuery("SELECT * FROM BOOKS WHERE ID = :id")
    Optional<Book> findById(@Bind("id") String id);

    @SqlQuery("SELECT * FROM BOOKS WHERE NAME = :name")
    Optional<Book> findByName(@Bind("name") String name);

    @SqlQuery("UPDATE BOOKS SET NAME = :name, AUTHOR = :author, ISBN = :isbn WHERE ID = :id")
    Optional update(@BindBean Book book);

    @SqlUpdate("INSERT INTO BOOKS(ID, NAME, AUTHOR, ISBN) " +
               "VALUES(:id, :name, :author, :isbn)")
    @GetGeneratedKeys
    String create(@BindBean Book book);

    @SqlUpdate("DELETE FROM BOOKS WHERE ID = :id")
    void delete(@Bind("id") String id);
}
