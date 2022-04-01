package com.library.services.db.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import com.library.services.db.dto.User;
import com.library.services.db.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

@RegisterRowMapper(UserMapper.class)
public interface UserDAO {

    @SqlQuery("SELECT * FROM USERS")
    List<User> findAll();

    @SqlQuery("SELECT * FROM USERS WHERE ID = :id")
    Optional<User> findById(@Bind("id") int id);

    @SqlQuery("SELECT * FROM USERS WHERE NAME = :name")
    Optional<User> findByName(@Bind("name") String name);

    @SqlQuery("UPDATE USERS SET NAME = :name, EMAIL = :email WHERE ID = :id")
    Optional update(@BindBean User user);

    @SqlUpdate("INSERT INTO USERS(ID, NAME, EMAIL) " +
            "VALUES(:id, :name, :email)")
    @GetGeneratedKeys
    int create(@BindBean User user);

    @SqlUpdate("DELETE FROM USERS WHERE ID = :id")
    void delete(@Bind("id") int id);
}
