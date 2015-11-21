package com.pizzaapp.model;

import java.io.Serializable;

/**
 * Created by Ryan on 11/21/2015.
 */

public class CreditCard implements Serializable {

    private String cvs;
    private String number;
    private String name;
    private String expiration;

    public CreditCard(){}

    public CreditCard(String n, String num, String e, String c){
        this.name = n;
        this.number = num;
        this.expiration = e;
        this.cvs = c;
    }

    public boolean validCVS(String cvs){
        if(cvs.length() != 3){
            return false;
        }
        else{
            String number = "0123456789";
            for(int i=0; i<cvs.length(); i++) {
                if (number.indexOf(cvs.charAt(i)) == -1) {
                    return false;
                }
            }
            return true;
        }
    }

    public String getCvs(){
        return cvs;
    }

    public void setCvs(String cvs){
        this.cvs = cvs;
    }

    public boolean validCC(String cardNumber){
        String number = "0123456789";
//        try{
//            int x = Integer.parseInt(cardNumber);
//        }catch(Exception e){
//            return false;
//        }
        for(int i=0; i<cardNumber.length(); i++) {
            if (number.indexOf(cardNumber.charAt(i)) == -1) {
                return false;
            }

        }
        if(cardNumber.length() != 16){
            return false;
        }
        else{
            return true;
        }
    }

    public String getNumber(){
        return number;
    }

    public void setNumber(String number){
        this.number = number;
    }


    public boolean validName(String name){
        if(name.contains(" ") && name.length() > 1){
            return true;
        }else {
            return false;
        }
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public boolean validExpiration(String exp){
        String year = exp.substring(3);
        String month = exp.substring(0,2);
        int m = Integer.parseInt(month);
        int y = Integer.parseInt(year);
        System.out.println("month " + m + "year " + y);
        if (y > 2015){
            if ( m > 0 && m < 13){
                return true;
            }else{
                return false;
            }
        }else if (y == 2015){
            if ( m == 12){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    public String getExpiration(){
        return expiration;
    }

    public void setExpiration(String expiration){
        this.expiration = expiration;
    }

}