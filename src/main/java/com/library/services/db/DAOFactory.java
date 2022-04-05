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
public final class DAOFactory {
    private BookDAO bookDAO;
    private UserDAO userDAO;
    private IssueDAO issueDAO;
    private LibraryManagementConfiguration configuration;
    private Environment environment;
    private static Jdbi jdbi;
    private static final DAOFactory INSTANCE = new DAOFactory();

    private DAOFactory() {
    }
    public static DAOFactory getInstance() {
        return INSTANCE;
    }

    public void init(Environment environment, LibraryManagementConfiguration configuration) {

        if (INSTANCE.configuration == null && INSTANCE.environment == null) {
            INSTANCE.configuration = configuration;
            INSTANCE.environment = environment;
            jdbi = new JdbiFactory().build(environment, configuration.getLibraryManagementDataSourceFactory(), "libraryDB");
        }

        bookDAO = jdbi.onDemand(BookDAO.class);
        userDAO = jdbi.onDemand(UserDAO.class);
        issueDAO = jdbi.onDemand(IssueDAO.class);
    }
}
