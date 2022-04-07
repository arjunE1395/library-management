package com.library.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.*;
import org.redisson.config.Config;

import javax.validation.constraints.*;
import java.io.File;
import java.io.IOException;

@Data
@Slf4j
public class LibraryManagementConfiguration extends Configuration {
    @JsonProperty("defaultName")
    private String defaultName = "LibraryManagement";

    @JsonProperty("env")
    private String env;

    @JsonProperty("libraryDatabase")
    private DataSourceFactory libraryManagementDataSourceFactory;

    @JsonProperty("httpClient")
    private HttpClientConfiguration httpClientConfig;

    @JsonProperty("redissionFileName")
    private String redissionConfigFile;

    @JsonIgnore
    private Config distributedCacheConfig;

    public Config getDistributedCacheConfig() {
        if (distributedCacheConfig == null) {
            log.info("ConfigFile: " + this.redissionConfigFile);
            try {
                distributedCacheConfig = Config.fromYAML(new File("src/main/resources/" + this.redissionConfigFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return distributedCacheConfig;
    }
}
