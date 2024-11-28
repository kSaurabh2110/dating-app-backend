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
import java.sql.Timestamp;

@Repository
public class UserRepository {
    private static final Logger logger = LogManager.getLogger(UserRepository.class);
    @Autowired
    private PostgresDataSource postgresDataSource;

    // Method to save a new user
    public void saveUser(User user) throws Exception {
        String sql = "INSERT INTO users (phone_number, password) VALUES (?, ?)";
        Connection connection = postgresDataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int index=1;
        try {
            preparedStatement.setString(index++, user.getPhoneNumber());
            preparedStatement.setString(index++, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(CorrelationIdGenerator.getUniqueRequestIdLogging()+"Error while saving user: " + e.getMessage());
            throw new RuntimeException();
        }finally {
            postgresDataSource.close(preparedStatement,connection);
        }
    }

    // Method to find user by phone number
    public User findUserByPhoneNumber(String phoneNumber) throws Exception {
        String sql = "SELECT * FROM users WHERE phone_number = ?";
        Connection connection = postgresDataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = null;
        int index = 1;
        try {
            preparedStatement.setString(index++, phoneNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User(resultSet);
                return user;
            }
            return null;
        }catch (SQLException e) {
            logger.error(CorrelationIdGenerator.getUniqueRequestIdLogging()+"Error while findUserByPhoneNumber :: " + e.getMessage());
            throw new RuntimeException();
        }finally {
            postgresDataSource.close(resultSet,preparedStatement,connection);
        }
    }

    // Method to update user status after profile completion
    public void updateUserProfile(User user) throws Exception {
        String sql = "UPDATE users SET profile_completed_status = ?, date_of_birth = ?, gender = ?, first_name = ?, last_name = ?, location = ? WHERE phone_number = ?";
        Connection connection = postgresDataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int index = 1;
        try {
            preparedStatement.setBoolean(index++, user.getProfileCompletedStatus());
            preparedStatement.setTimestamp(index++, user.getDateOfBirth() != null ? Timestamp.valueOf(user.getDateOfBirth().atStartOfDay()) : null);
            preparedStatement.setString(index++, user.getGender());
            preparedStatement.setString(index++, user.getFirstName());
            preparedStatement.setString(index++, user.getLastName());
            preparedStatement.setString(index++, user.getLocation());
            preparedStatement.setString(index++, user.getPhoneNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(CorrelationIdGenerator.getUniqueRequestIdLogging()+"Error while findUserByPhoneNumber :: " + e.getMessage());
            throw new RuntimeException();
        }finally {
            postgresDataSource.close(preparedStatement,connection);
        }
    }
}
