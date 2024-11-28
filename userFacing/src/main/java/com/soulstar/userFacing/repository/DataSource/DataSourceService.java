package com.soulstar.userFacing.repository.DataSource;

import com.soulstar.userFacing.config.CorrelationIdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public abstract class DataSourceService {
    private static final Logger logger = LogManager.getLogger(DataSourceService.class);
    private static Set<Connection> connectionSet = new HashSet<>();
    protected DataSource dataSource;

    public Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        synchronized (connectionSet) {
            logger.info(CorrelationIdGenerator.getUniqueRequestIdLogging()+"Printing connection size before adding a new connection:: " + connectionSet.size());
            connectionSet.add(connection);
        }
        return connection;
    }

    public <T extends AutoCloseable> void close(T... objects) throws Exception {
        for (T object : objects) {
            if(object == null) continue;
            if(!(object instanceof Connection)){
                object.close();
                continue;
            }
            synchronized (connectionSet) {
                connectionSet.remove(object);
                logger.info(CorrelationIdGenerator.getUniqueRequestIdLogging()+"Printing connection size after adding removing :: " + connectionSet.size());
            }
            object.close();
        }
    }
}
