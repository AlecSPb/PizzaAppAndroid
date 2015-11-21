package com.pizzaapp.model;

import java.io.Serializable;

public class Address implements Serializable {

    private String address1;
    private String address2;
    private String city;
    private String locality;
    private String postalCode;
    private String country;

    public Address (){}

    public Address (String address1, String address2, String city, String locality, String postalCode, String country){
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.locality = locality;
        this.postalCode = postalCode;
        this.country = country;
    }

    public boolean equals (Address x){
        if(this.address1.equalsIgnoreCase(x.address1) && this.address2.equalsIgnoreCase(x.address2)
                && this.city.equalsIgnoreCase(x.city) && this.locality.equalsIgnoreCase(x.locality)
                && this.postalCode.equalsIgnoreCase(x.postalCode) && this.country.equalsIgnoreCase(x.country)){
            return true;
        }else{
            return false;
        }
    }

    public String toString(){
        String str = address1 + "\n";
        if(address2 == null || address2.equals("")){
            str += address2 + "\n";
        }
        str += city + ", " + locality + ", " + postalCode + "\n";
        return str;
    }

    public boolean validState(String state){
        if (state.length() != 2){
            return false;
        }else{
            return true;
        }
    }

    public boolean validZip(String Zip){
        String number = "0123456789";

        for(int i=0; i<Zip.length(); i++) {
            if (number.indexOf(Zip.charAt(i)) == -1) {
                return false;
            }

        }
        if (Zip.length() != 5){
            return false;
        }
        return true;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1){
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2){
        this.address2 = address2;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getLocality(){
        return locality;
    }

    public void setLocality(String locality){
        this.locality = locality;
    }

    public String getPostalCode(){
        return postalCode;
    }

    public void setPostalCode(String postalCode){
        this.postalCode = postalCode;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country){
        this.country = country;
    }

}
