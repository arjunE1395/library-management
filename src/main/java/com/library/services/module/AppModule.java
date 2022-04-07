package com.library.services.module;

import com.google.inject.AbstractModule;
import com.library.services.db.DAOFactory;
import com.library.services.db.dao.BookDAO;
import com.library.services.db.dao.IssueDAO;
import com.library.services.db.dao.UserDAO;
import com.library.services.managed.DistributedCacheProvider;

public class AppModule extends AbstractModule {
    private final DAOFactory daoFactory;
    private final DistributedCacheProvider distributedCacheProvider;

    public AppModule(DAOFactory daoFactory, DistributedCacheProvider distributedCacheProvider) {
        this.daoFactory = daoFactory;
        this.distributedCacheProvider = distributedCacheProvider;
    }

    @Override
    protected void configure() {
        bind(BookDAO.class).toInstance(daoFactory.getBookDAO());
        bind(UserDAO.class).toInstance(daoFactory.getUserDAO());
        bind(IssueDAO.class).toInstance(daoFactory.getIssueDAO());
        bind(DistributedCacheProvider.class).toInstance(distributedCacheProvider);
    }
}
