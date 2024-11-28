package com.soulstar.userFacing.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
public class DatabaseConfig {

    @Primary
    @Bean
    @Qualifier("postgresDataSource")
    public DataSource psqlDataSource(Environment env){
        String jdbcDriver = env.getProperty("com.soulstar.userFacing.config.DatabaseConfig.datasource.psql.driver-class-name");
        String jdbcUrl = env.getProperty("com.soulstar.userFacing.config.DatabaseConfig.datasource.psql.url");
        String userName = env.getProperty("com.soulstar.userFacing.config.DatabaseConfig.datasource.psql.username");
        String password = env.getProperty("com.soulstar.userFacing.config.DatabaseConfig.datasource.psql.password");
        Integer maxActive = Integer.valueOf(Objects.requireNonNull(env.getProperty("com.soulstar.userFacing.config.DatabaseConfig.datasource.psql.maxActive")));
        Integer maxIdle = Integer.valueOf(Objects.requireNonNull(env.getProperty("com.soulstar.userFacing.config.DatabaseConfig.datasource.psql.maxIdle")));
        Long maxWait = Long.valueOf(Objects.requireNonNull(env.getProperty("com.soulstar.userFacing.config.DatabaseConfig.datasource.psql.maxWait")));
        Long minEvictableIdleTime = Long.valueOf(Objects.requireNonNull(env.getProperty("com.soulstar.userFacing.config.DatabaseConfig.datasource.psql.minEvictableIdleTime")));
        Long timeBetweenEvictionRunsMillis = Long.valueOf(Objects.requireNonNull(env.getProperty("com.soulstar.userFacing.config.DatabaseConfig.datasource.psql.timeBetweenEvictionRunsMillis")));
        return getDataSource(jdbcDriver, jdbcUrl, userName, password, maxActive, maxIdle, maxWait, minEvictableIdleTime, timeBetweenEvictionRunsMillis);

    }
    private DataSource getDataSource(String jdbcDrive, String jdbcUrl, String userName, String password, Integer maxActive, Integer maxIdle, Long maxWait, Long minEvictableIdleTime, Long timeBetweenEvictionRunsMillis) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(jdbcDrive);
        basicDataSource.setUrl(jdbcUrl);
        basicDataSource.setUsername(userName);
        basicDataSource.setPassword(password);
        basicDataSource.setMaxActive(maxActive);
        basicDataSource.setMaxIdle(maxIdle);
        basicDataSource.setMaxWait(maxWait);
        basicDataSource.setAccessToUnderlyingConnectionAllowed(true);
        basicDataSource.setPoolPreparedStatements(true);
        basicDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTime);
        basicDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        return basicDataSource;
    }
}
