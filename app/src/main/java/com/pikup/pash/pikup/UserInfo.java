package com.pikup.pash.pikup;

import java.util.HashMap;
import java.util.Map;

public class UserInfo {
    private String firstName;
    private String lastName;
    private String address;
    private String state;
    private String city;
    private String zipCode;
    private String dateOfBirth;
    private String email;
    private Map<String, Boolean> posts;

    //non argument consturction for user info
    public UserInfo() {}

    // User info constructor(instance of an object is created)
    public UserInfo(String firstName, String lastName, String address, String state, String city,
                    String zipCode, String dateOfBirth, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.posts = new HashMap<String, Boolean>();
    }

    //getter and setter for the above defined strings

    //returning the user firstname and lastname
    public String getFullName() {
        return firstName + " " + lastName;
    }

    //setting the user full name
    public void setFullName(String fullName) {
        int len = fullName.length();
        for (int i = 0; i < len; i++) {
            char k = fullName.charAt(i);
            if (k == ' ') {
                firstName = fullName.substring(0, i);
                lastName = fullName.substring(i, len - 1);
                return;
            }
            if (i == len - 1) {
                firstName = fullName;
                lastName = "";
            }
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Boolean> getPosts() {
        return posts;
    }

    public void setPosts(Map<String, Boolean> posts) {
        this.posts = posts;
    }
}
