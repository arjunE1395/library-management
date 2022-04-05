package com.library.services.module;

import com.google.inject.AbstractModule;
import com.library.services.db.DAOFactory;
import com.library.services.db.dao.BookDAO;
import com.library.services.db.dao.IssueDAO;
import com.library.services.db.dao.UserDAO;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BookDAO.class).toInstance(DAOFactory.getInstance().getBookDAO());
        bind(UserDAO.class).toInstance(DAOFactory.getInstance().getUserDAO());
        bind(IssueDAO.class).toInstance(DAOFactory.getInstance().getIssueDAO());
    }
}
