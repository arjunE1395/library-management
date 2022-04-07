package com.library.services.db.mapper;

import com.library.services.db.dto.Issue;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IssueMapper extends BaseRowMapper<Issue> {
    @Override
    Issue map(ResultSet resultSet) throws SQLException {
        return new Issue(
                resultSet.getString("BOOK_ID"),
                resultSet.getString("USER_ID")
        );
    }
}
