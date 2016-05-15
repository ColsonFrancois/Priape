package com.example.francois.priape.Model;

/**
 * Created by Francois on 15/05/2016.
 */
public class Adress {

    private String street;
    private String country;
    private  String city;
    private String zip;


    public Adress(String street, String city, String country)
    {
        this.street = street;
        this.country = country;
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
