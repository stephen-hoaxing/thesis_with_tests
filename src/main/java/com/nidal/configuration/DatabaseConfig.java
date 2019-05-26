package com.nidal.configuration;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Nidal on 2017.10.22..
 */

@Configuration
@PropertySource("classpath:application.properties")
@EnableNeo4jRepositories(basePackages = "com.nidal.repo")
@EnableTransactionManagement
public class DatabaseConfig extends Neo4jConfiguration {

    @Value("${neo4j.username}")
    private String userName;

    @Value("${neo4j.password}")
    private String password;

    @Value("${neo4j.port}")
    private String port;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig () {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(), "com.nidal.model");
    }

    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
        String uri = String.format("http://%s:%s@localhost:%s", userName, password, port);
        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration();
        configuration
                .driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setURI(uri);
        return configuration;
    }

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Session getSession() throws Exception {
        return super.getSession();
    }

}
