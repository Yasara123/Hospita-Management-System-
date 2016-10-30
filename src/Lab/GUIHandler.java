/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Lab;

import database.LabDataBase;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hasangi Thathsarani
 */
public class GUIHandler {
         LabDataBase db;

    public GUIHandler() {
        db = LabDataBase.getInstance();
    }
    public boolean insertTestName(String text,double bill){
    return db.insertTestName(text,bill);
               }
     public Vector DisplayDataComboBox(){
             try {
                 return db.DisplayDataComboBox();
             } catch (SQLException ex) {
                 Logger.getLogger(GUIHandler.class.getName()).log(Level.SEVERE, null, ex); 
                 return null;
             }
            
     }
          public boolean createTestTables(String testName){
              return db.createTestTables(testName);
              
          } 
           public boolean insertDataNewTest(String testName,String ChemName,double Quantity){
               return db.insertDataNewTest(testName, ChemName, Quantity);  
           }
           public int getTestNo(String testName){
           return db.getTestNo(testName);
           }
           public boolean insertToPatientHistory(int id,String testName){
               return db.insertToPatientHistory(id, testName);
           }
           public boolean updateStockTable(String str,String quantity){
               return db.updateStockTable(str, quantity);
           }
            public  DefaultTableModel showStocks(){
                return db.showStocks();
            }
            public  DefaultTableModel showSuppliers(){
                return db.showSuppliers();
            }
            public  DefaultTableModel showTests(){
            return db.showTests();
            }
            public Vector DisplayTestsCombo(int patientID){
                return db.DisplayTestsCombo(patientID);
            }
            public boolean createTestTemp(int patientID){
                return db.createTestTemp(patientID);
            }
             public boolean insertTestResults(int patientID,String content,double result){
                 return db.insertTestResults(patientID, content, result);
             }
             public boolean stockDecrease(String testName){
                 return db.stockDecrease(testName);
             }
                 public Vector DisplayTestComboBox() throws SQLException{
                     return db.DisplayTestComboBox();
                 }
             public boolean checkStockLimits(){
                 int temp=0;
                Vector<Double> data = new Vector<>();
                 data = db.checkStockLimits();
                 for(int i=0;i<data.size();i++){
                 if(data.get(i)<1000)
                    temp++;
             }
                 if (temp>0)
                     return true;
                 else
                     return false;
             }
              public boolean updatePatientHistory(int id,String name){
                  return db.updatePatientHistory(id, name);
              }
}
