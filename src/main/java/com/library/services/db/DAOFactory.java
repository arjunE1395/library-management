package com.library.services.db;

import com.google.inject.Inject;
import io.dropwizard.setup.Environment;
import lombok.Getter;

import io.dropwizard.jdbi3.JdbiFactory;
import org.jdbi.v3.core.Jdbi;
import com.library.services.LibraryManagementConfiguration;
import com.library.services.db.dao.BookDAO;
import com.library.services.db.dao.IssueDAO;
import com.library.services.db.dao.UserDAO;

@Getter
public class DAOFactory {
    private final BookDAO bookDAO;
    private final UserDAO userDAO;
    private final IssueDAO issueDAO;

    public DAOFactory(Environment environment, LibraryManagementConfiguration configuration) {
        Jdbi jdbi = new JdbiFactory().build(environment, configuration.getLibraryManagementDataSourceFactory(), "libraryDB");
        bookDAO = jdbi.onDemand(BookDAO.class);
        userDAO = jdbi.onDemand(UserDAO.class);
        issueDAO = jdbi.onDemand(IssueDAO.class);
    }
}
