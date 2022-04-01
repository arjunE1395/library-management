package com.library.services;

import com.library.services.core.impl.BookManagerImpl;
import com.library.services.core.impl.IssueManagerImpl;
import com.library.services.core.impl.UserManagerImpl;
import com.library.services.db.DAOFactory;
import com.library.services.provider.AppConfigProvider;
import com.library.services.resources.IssueResource;
import com.library.services.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import com.library.services.resources.BookResource;

@Slf4j
public class LibraryManagementApplication extends Application<LibraryManagementConfiguration> {

    public static void main(final String[] args) throws Exception {
        log.info("Starting LibraryManagement");
        new LibraryManagementApplication().run(args);
    }

    @Override
    public String getName() {
        return "LibraryManagement";
    }

    @Override
    public void initialize(final Bootstrap<LibraryManagementConfiguration> bootstrap) {

        bootstrap.addBundle(new MigrationsBundle<>() {

            @Override
            public PooledDataSourceFactory getDataSourceFactory(LibraryManagementConfiguration config) {
                return config.getLibraryManagementDataSourceFactory();
            }

            @Override
            public String getMigrationsFileName() {
                return "library-db-migrations.xml";
            }

            @Override
            public String name() {
                return "library_db";
            }
        });

        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor())
        );
    }

    @Override
    public void run(final LibraryManagementConfiguration configuration,
                    final Environment environment) {
        AppConfigProvider.getInstance().init(configuration);

        final DAOFactory daoFactory = new DAOFactory(environment, configuration);

        final HttpClient httpClient = new HttpClientBuilder(environment)
                .using(configuration.getHttpClientConfig())
                .build(getName());

        log.info("Http client connection timeout [{}] time out [{}] request timeout [{}]",
                configuration.getHttpClientConfig().getConnectionTimeout(),
                configuration.getHttpClientConfig().getTimeout(),
                configuration.getHttpClientConfig().getConnectionRequestTimeout());

        BookManagerImpl bookManager = new BookManagerImpl(daoFactory.getBookDAO());
        UserManagerImpl userManager = new UserManagerImpl(daoFactory.getUserDAO());
        IssueManagerImpl issueManager = new IssueManagerImpl(daoFactory.getIssueDAO(), daoFactory.getBookDAO(), daoFactory.getUserDAO());

        environment.jersey().register(new BookResource(bookManager));
        environment.jersey().register(new UserResource(userManager));
        environment.jersey().register(new IssueResource(issueManager));

        log.info("Configured LibraryManagement");
    }
}
