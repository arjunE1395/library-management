package com.library.services.db.mapper;

import com.library.services.db.dto.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper extends BaseRowMapper<User> {
    @Override
    User map(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getString("NAME"),
                resultSet.getString("EMAIL")
        );
    }
}
