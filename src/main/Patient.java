/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

/**
 *
 * @author Kasun
 */
public class Patient {
    String firstName,LastName,NIC,contactNo,address,ID,age;
    
    public Patient(String ID,String firstName,String LastName,String Age,String NIC,String contactNo,String address){
        this.firstName=firstName;
        this.LastName=LastName;
        this.NIC=NIC;
        this.contactNo=contactNo;
        this.address=address;
        this.ID=ID;
        this.age=Age;
    }
    public String getID(){
        return ID;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return LastName;
    }
    public String getNIC(){
        return NIC;
    }
    public String getContactNo(){
        return contactNo;
    }
    public String getAddress(){
        return address;
    }
    public String getAge(){
        return age;
    }
    
}
