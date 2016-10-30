/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pharmasist;

/**
 *
 * @author Chamil
 */
public class medicine {

    
    int medID, quantity;
    float price;
    String medName, Dealer_Details;
    //Constructor for the Medicine class
    public medicine(int medID, String medName, int quantity, float price, String Dealer_Details) {
        this.medID = medID;
        this.medName = medName;
        this.quantity = quantity;
        this.price = price;
        this.Dealer_Details = Dealer_Details;
    }
    // get medicine id
    public int getMedID() {
        return medID;
    }
// get the quantity
    public int getQuantity() {
        return quantity;
    }
    // get the price
    public float getPrice() {
        return price;
    }
    // get the medicine name
    public String getMedName() {
        return medName;
    }
    // get the details of the supplier
    public String getDealer_Details() {
        return Dealer_Details;
    }
}
