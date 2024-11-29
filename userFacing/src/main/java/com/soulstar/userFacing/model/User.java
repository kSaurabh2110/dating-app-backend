package com.soulstar.userFacing.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private String phoneNumber;
    private String password; // Hashed password
    private LocalDate dateOfBirth;
    private String gender;
    private String location;
    private String firstName;
    private String lastName;
    private Boolean profileCompletedStatus;

    public User() {
    }

    public User(ResultSet resultSet) throws SQLException {
        this.setPhoneNumber(resultSet.getString("phone_number"));
        this.setPassword(resultSet.getString("password"));
        this.setDateOfBirthFromTimestamp(resultSet.getTimestamp("date_of_birth"));
        this.setGender(resultSet.getString("gender"));
        this.setFirstName(resultSet.getString("first_name"));
        this.setLastName(resultSet.getString("last_name"));
        this.setLocation(resultSet.getString("location"));
        this.setProfileCompletedStatus(resultSet.getBoolean("profile_completed_status"));
    }

    private void setDateOfBirthFromTimestamp(Timestamp dob) {
        if (dob==null){
            return;
        }
        this.setDateOfBirth(dob.toLocalDateTime().toLocalDate());
    }
}
