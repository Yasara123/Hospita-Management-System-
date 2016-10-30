/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Doctor;

/**
 *
 * @author Yas
 */
public class Prescription {
    String docName,patientName,date,medList,notes;
    //prescription constructor
    public Prescription(String docName,String patientName , String date, String medList,String notes){
        this.docName=docName;
        this.date=date;
        this.medList=medList;
        this.notes=notes;
        this.patientName=patientName;
    }
    // get doctor's name
    public String getDocName(){
        return docName;
    }
    // get Patient's name
    public String getpatientName(){
        return patientName;
    }
    // get Medicine list
    public String getmedList(){
        return medList;
    }
    // get date
    public String getdate(){
        return date;
    }
    //get any note
    public String getnotes(){
        return notes;
    }

}
