package com.library.services.module;

import com.google.inject.AbstractModule;
import com.library.services.db.DAOFactory;
import com.library.services.db.dao.BookDAO;
import com.library.services.db.dao.IssueDAO;
import com.library.services.db.dao.UserDAO;

public class AppModule extends AbstractModule {
    private DAOFactory daoFactory;

    public AppModule(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    protected void configure() {
        bind(BookDAO.class).toInstance(daoFactory.getBookDAO());
        bind(UserDAO.class).toInstance(daoFactory.getUserDAO());
        bind(IssueDAO.class).toInstance(daoFactory.getIssueDAO());
    }
}
