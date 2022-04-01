package com.library.services.db.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import com.library.services.db.dto.Issue;
import com.library.services.db.mapper.IssueMapper;

import java.util.List;
import java.util.Optional;

@RegisterRowMapper(IssueMapper.class)
public interface IssueDAO {

    @SqlQuery("SELECT * FROM ISSUES")
    List<Issue> findAll();

    @SqlQuery("SELECT * FROM ISSUES WHERE ID = :id")
    Optional<Issue> findById(@Bind("id") int id);

    @SqlQuery("SELECT * FROM ISSUES WHERE BOOK_ID = :bookID")
    Optional<Issue> findByBookId(@Bind("bookID") int bookID);

    @SqlQuery("SELECT * FROM ISSUES WHERE USER_ID = :userID")
    List<Issue> findByUserId(@Bind("userID") int userID);

    @SqlQuery("UPDATE ISSUES SET BOOK_ID = :bookID, USER_ID = :userID WHERE ID = :id")
    Optional update(@BindBean Issue issue);

    @SqlUpdate("INSERT INTO ISSUES(ID, BOOK_ID, USER_ID) " +
               "VALUES(:id, :bookID, :userID)")
    @GetGeneratedKeys
    int create(@BindBean Issue issue);

    @SqlUpdate("DELETE FROM ISSUES WHERE ID = :id")
    void delete(@Bind("id") int id);
}
