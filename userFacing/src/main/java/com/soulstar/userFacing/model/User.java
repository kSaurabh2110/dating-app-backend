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
    private String name;
    private String gender;
    private String dob;
    private String location;

    public User() {
    }

    public User(ResultSet resultSet) throws SQLException {
        this.setPhoneNumber(resultSet.getString("phone_number"));
        this.setGender(resultSet.getString("gender"));
        this.setName(resultSet.getString("name"));
        this.setLocation(resultSet.getString("location"));
        this.setDob(resultSet.getString("dob"));
    }
}
