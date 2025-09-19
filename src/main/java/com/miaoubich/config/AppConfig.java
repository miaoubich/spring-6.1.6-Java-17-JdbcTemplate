package com.miaoubich.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DataSourceConfig.class})//, SecurityConfig.class})
public class AppConfig {

}
