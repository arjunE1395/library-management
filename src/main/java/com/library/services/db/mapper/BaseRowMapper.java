package com.library.services.db.mapper;

import com.library.services.db.dto.BaseDTO;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public abstract class BaseRowMapper<T extends BaseDTO> implements RowMapper<T> {

    @Override
    public T map(ResultSet resultSet, StatementContext ctx) throws SQLException {
        String id = resultSet.getString("ID");
        int status = resultSet.getInt("STATUS");
        Date createdAt = resultSet.getTimestamp("CREATED_AT");
        Date modifiedAt = resultSet.getTimestamp("MODIFIED_AT");

        T dto = map(resultSet);

        dto.setId(id);
        dto.setStatus(status);
        dto.setCreatedAt(createdAt);
        dto.setModifiedAt(modifiedAt);

        return dto;
    }

    abstract T map(ResultSet resultSet) throws SQLException;
}
