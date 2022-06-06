package com.example.auth_service.config;

import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@Profile({"local"}) // set Local DB server for h2
public class H2ServerConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource() throws SQLException {
        Server.createTcpServer("-tcp",
                "-tcpPort",
                "9091",
                "-tcpAllowOthers",
                "-ifNotExists"
        ).start();
        return new HikariDataSource();
    }
}
