package com.pikup.pash.pikup;

/**
 * Created by pash on 3/24/17.
 */

public class UserInfo {
    private String FirstName;
    private String LastName;
    private String Address;
    private String State;
    private String City;
    private String ZipCode;
    private String DateOfBirth;
    private String Email;

    public UserInfo(){

    }

    public UserInfo(String firstName, String lastName, String address, String state, String city,
                    String zipCode, String dateOfBirth, String email){
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Address = address;
        this.State = state;
        this.City = city;
        this.ZipCode = zipCode;
        this.DateOfBirth = dateOfBirth;
        this.Email = email;
    }
    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }


    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}

