package com.library.services;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.library.services.db.DAOFactory;
import com.library.services.module.AppModule;
import com.library.services.provider.AppConfigProvider;
import com.library.services.resources.IssueResource;
import com.library.services.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
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
        DAOFactory.getInstance().init(environment, configuration);

        /* Dependency Injection */
        Injector injector = Guice.createInjector(new AppModule());

        log.info("Http client connection timeout [{}] time out [{}] request timeout [{}]",
                configuration.getHttpClientConfig().getConnectionTimeout(),
                configuration.getHttpClientConfig().getTimeout(),
                configuration.getHttpClientConfig().getConnectionRequestTimeout());

        BookResource bookResource = injector.getInstance(BookResource.class);
        UserResource userResource = injector.getInstance(UserResource.class);
        IssueResource issueResource = injector.getInstance(IssueResource.class);

        environment.jersey().register(bookResource);
        environment.jersey().register(userResource);
        environment.jersey().register(issueResource);

        log.info("Configured LibraryManagement");
    }
}
