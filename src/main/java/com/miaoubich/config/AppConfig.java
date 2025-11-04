package com.miaoubich.config;

import com.miaoubich.security.config.SecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.miaoubich")
@Import({DataSourceConfig.class, SecurityConfig.class})
public class AppConfig {

}
