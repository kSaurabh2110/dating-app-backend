package com.soulstar.userFacing.repository;

import com.soulstar.userFacing.config.CorrelationIdGenerator;
import com.soulstar.userFacing.model.User;
import com.soulstar.userFacing.repository.DataSource.PostgresDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {
    private static final Logger logger = LogManager.getLogger(UserRepository.class);
    @Autowired
    private PostgresDataSource postgresDataSource;


    // Method to find user by phone number
    public User findUserByUid(String uid) throws Exception {
        String sql = "SELECT * FROM users WHERE uid = ?";
        Connection connection = postgresDataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = null;
        int index = 1;
        try {
            preparedStatement.setString(index++, uid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User(resultSet);
                return user;
            }
            return null;
        }catch (SQLException e) {
            logger.error(CorrelationIdGenerator.getUniqueRequestIdLogging()+"Error while findUserByUid :: " + e.getMessage());
            throw new RuntimeException();
        }finally {
            postgresDataSource.close(resultSet,preparedStatement,connection);
        }
    }
}
