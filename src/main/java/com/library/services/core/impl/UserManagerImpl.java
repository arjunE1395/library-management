package com.library.services.core.impl;

import com.google.inject.Inject;
import com.library.services.db.dao.UserDAO;
import com.library.services.db.dto.User;
import com.library.services.managed.DistributedCacheProvider;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.statement.UnableToCreateStatementException;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.redisson.api.RMap;

import javax.ws.rs.WebApplicationException;

@Slf4j
public class UserManagerImpl {
    private final UserDAO userDAO;
    private final DistributedCacheProvider distributedCacheProvider;
    private RMap<String, User> userRMap;

    @Inject
    public UserManagerImpl(UserDAO userDAO, DistributedCacheProvider distributedCacheProvider) {
        this.userDAO = userDAO;
        this.distributedCacheProvider = distributedCacheProvider;
        userRMap = this.distributedCacheProvider.getUserMap();
    }

    public Object viewUser(String user_id) {
        if (user_id == null) {
            return userDAO.findAll();
        } else {
            return  userRMap.computeIfAbsent(
                    user_id,
                    user -> userDAO.findById(user_id).orElseThrow(() -> {
                        log.info("Invalid User-ID");
                        throw new WebApplicationException("Invalid User-ID: " + user_id,400);
                    })
            );
        }
    }

    public String addUser(User user) {
        try {
            String user_id = userDAO.create(user);
            userRMap.remove(user_id);
            return user_id;
        } catch (UnableToExecuteStatementException | UnableToCreateStatementException e) {
            log.error("Unable to execute statement");
            throw new WebApplicationException("Unable to execute statement", 500);
        }
    }
}
