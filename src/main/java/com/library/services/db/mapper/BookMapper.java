package com.library.services.db.mapper;

import com.library.services.db.dto.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper extends BaseRowMapper<Book> {
    @Override
    Book map(ResultSet resultSet) throws SQLException {
        return new Book(
                resultSet.getString("NAME"),
                resultSet.getString("AUTHOR"),
                resultSet.getString("ISBN")
        );
    }
}
