package com.library.services.db;

import io.dropwizard.setup.Environment;
import lombok.Getter;

import io.dropwizard.jdbi3.JdbiFactory;
import org.jdbi.v3.core.Jdbi;
import com.library.services.LibraryManagementConfiguration;
import com.library.services.db.dao.BookDAO;
import com.library.services.db.dao.IssueDAO;
import com.library.services.db.dao.UserDAO;

import java.util.HashMap;
import java.util.Map;

@Getter
public final class DAOFactory {
    private Jdbi jdbi;
    private Map<String, Object> daoMap = new HashMap<>();

    public DAOFactory(Environment environment, LibraryManagementConfiguration configuration) {
        final JdbiFactory factory = new JdbiFactory();
        jdbi = factory.build(environment, configuration.getLibraryManagementDataSourceFactory(), "libraryDB");
        populateDAOMap();
    }

    private void populateDAOMap() {
        if (daoMap != null) {
            daoMap.put(BookDAO.class.getCanonicalName(), jdbi.onDemand(BookDAO.class));
            daoMap.put(UserDAO.class.getCanonicalName(), jdbi.onDemand(UserDAO.class));
            daoMap.put(IssueDAO.class.getCanonicalName(), jdbi.onDemand(IssueDAO.class));
        }
    }

    public BookDAO getBookDAO() {
        return (BookDAO) daoMap.get(BookDAO.class.getCanonicalName());
    }

    public UserDAO getUserDAO() {
        return (UserDAO) daoMap.get(UserDAO.class.getCanonicalName());
    }

    public IssueDAO getIssueDAO() {
        return (IssueDAO) daoMap.get(IssueDAO.class.getCanonicalName());
    }
}
