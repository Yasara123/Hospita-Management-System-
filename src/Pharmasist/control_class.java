/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Pharmasist;

import main.Patient;
import database.PatientRecordsDB;
import database.medicine_DB;

/**
 *
 * @author Chamil
 */
public class control_class {
    //making the class singleton
    PatientRecordsDB data = PatientRecordsDB.getInstance();
    Patient aPatient ;
    
    medicine_DB med_DB = medicine_DB.getInstance();
    medicine aMedicine;
    
 
    
    float total;
    double sum = 0.0;
    private static control_class instance=new control_class();;
    public control_class(){
        
    }
    //Implement singleton pattern
    public static control_class getInstance(){
          if(instance==null)
              instance=new control_class();
          return instance;
      }
    // to get the bill for the pharmacy
    float getTotalBill(int medquantity, String medName ){        
        total=0;
        total = medquantity*med_DB.getPriceByname(medName);     
        //System.out.println(medName+" "+medquantity);
        sum+=total;
        return 0;
        
    }
    
    //update the the quantity of medicine after submitting
    void updateDatabaseAfterSubmitting(String medText1, int medq1){
        
        med_DB.updateDatabaseAfterSubmitting(medText1, medq1) ;
    }
    
    //set the bill of paties to the databases
    void setsetbilltothetable(String pID,String pName, String price) {
        
        med_DB.setBill(pID,pName,price);
    }
    
    //set sum to zero
    void setSum(){
        sum=0;
    }
    
    void updatebill(){
        //pharbillText.setText(""+control.sum);
    }
    
    
}
