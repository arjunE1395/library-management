package com.library.services.provider;

import com.library.services.LibraryManagementConfiguration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppConfigProvider {
    private static AppConfigProvider instance = new AppConfigProvider();
    private LibraryManagementConfiguration configuration;

    private AppConfigProvider() {
    }

    public static AppConfigProvider getInstance() {
        return instance;
    }

    public void init(LibraryManagementConfiguration libraryManagementConfiguration) {
        if (configuration == null) {
            configuration = libraryManagementConfiguration;
        } else {
            log.info("Application configuration can be set only once");
            throw new IllegalStateException("Application configuration can be set only once");
        }
    }

    public LibraryManagementConfiguration getConfiguration() {
        if (configuration == null) {
            log.info("Application configuration is not set");
            throw new IllegalStateException("Application configuration is not set");
        }
        return configuration;
    }
}
