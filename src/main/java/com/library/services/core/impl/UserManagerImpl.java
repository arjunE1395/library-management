package com.library.services.core.impl;

import com.google.inject.Inject;
import com.library.services.db.dao.UserDAO;
import com.library.services.db.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.statement.UnableToCreateStatementException;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;

import javax.ws.rs.WebApplicationException;

@Slf4j
public class UserManagerImpl {
    @Inject
    private UserDAO userDAO;

    public Object viewUser(Integer user_id) {
        if (user_id == null) {
            return userDAO.findAll();
        } else {
            return userDAO.findById(user_id);
        }
    }

    public int addUser(User user) {
        try {
            return userDAO.create(user);
        } catch (UnableToExecuteStatementException | UnableToCreateStatementException e) {
            log.error("Unable to execute statement");
            throw new WebApplicationException("Unable to execute statement", 500);
        }
    }
}
