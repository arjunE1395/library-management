package com.library.services;

import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;

@Data
public class LibraryManagementConfiguration extends Configuration {
    // TODO: implement service configuration
    @JsonProperty("defaultName")
    private String defaultName = "LibraryManagement";

    @JsonProperty("env")
    private String env;

    @JsonProperty("libraryDatabase")
    private DataSourceFactory libraryManagementDataSourceFactory;

    @JsonProperty("httpClient")
    private HttpClientConfiguration httpClientConfig;

}
