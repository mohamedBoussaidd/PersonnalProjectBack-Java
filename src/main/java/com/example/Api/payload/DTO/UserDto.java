package com.example.Api.payload.DTO;

import java.sql.Date;

public class UserDto {
    private Long id;
    private String name;
    private String firstName;
    private String email;
    private Date dateOfBirth;


    public UserDto(Long id, String name, String firstName, String email, Date dateOfBirth) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
