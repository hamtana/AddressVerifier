package com.example.AddressVerifier.model;

public class Address {

    private String fullAddress;
    public String streetAddress;
    private String suburb;
    private String town;

    /**
     * Default constructor
     */
    public Address() {}

    public Address(String fullAddress, String suburb, String town) {
        this.fullAddress = fullAddress;
        this.suburb = suburb;
        this.town = town;
        this.streetAddress = fullAddress.substring(0, fullAddress.indexOf(","));
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @Override
    public String toString() {
        return streetAddress + ", " + suburb + ", " + town;
    }
}
