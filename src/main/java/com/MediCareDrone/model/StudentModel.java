package com.MediCareDrone.model;

import java.time.LocalDate;

public class StudentModel {
    private String firstName;
    private String lastName;
    private String username;
    private LocalDate dob;
    private String gender;
    private String email;
    private String phone;
    private String subject;
    private String password;

    // Constructor
    public StudentModel(String firstName, String lastName, String username, LocalDate dob, String gender,
                        String email, String phone, String subject, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.subject = subject;
        this.password = password;
    }
    
    public StudentModel(String firstName, String lastName, String username, String gender, String email, String phone, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }


    // Getters and setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}