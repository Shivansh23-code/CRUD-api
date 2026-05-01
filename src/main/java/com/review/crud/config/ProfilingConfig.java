package com.review.crud.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "profiling")
public class ProfilingConfig {
    private boolean enabled;
    private String loglevel;

}
