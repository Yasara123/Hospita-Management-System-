/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Doctor;

import main.Patient;
import database.PatientRecordsDB;
import java.util.Calendar;
import main.MyQueue;

/**
 *
 * @author Yas
 */
public class Doctor_UI_handler {
    // Making singleton this class
    private static Doctor_UI_handler instance;
    PatientRecordsDB data;
    Doctor aDoctor;
    // constructor of Doctor_UI_handler
    public Doctor_UI_handler(){
        data=PatientRecordsDB.getInstance();
    }
    // getInstances() method to implement the singleton design pattern
    public static Doctor_UI_handler getInstance(){
        if(instance==null)
            instance=new Doctor_UI_handler();
        return instance;
    }
    //Access the database using the Doctor's name in the GUI
    public void setDoctor(String userName){
        aDoctor=new Doctor(userName);
        aDoctor.setQueue(data.getDocQueue(userName));
    }
    // Returning no of patients remaning in the queue to GUI
    public int getRemainingPatientCount(){
        return aDoctor.getRemainingCount();
    }
    // Getting doctor's details from the database
    public String getDocName(){
        return data.getDocName(aDoctor.userName);
    }
    // Get the details of the current patient and return it to GUI
    public Patient getCurrentPatient(){
        return aDoctor.getCurrentPatient();
    }
    // call next patient in the queue
    public boolean callNextPatient(){
        if(aDoctor.nextPatient()!=null)
            return true;
        return false;
    }
    // update prescription database according to the given GUI details
    public void updatePrescription(MyQueue<prescriptionItem> queue,String notes){
        data.updatePrescription(aDoctor.getCurrentPatient().getID(), aDoctor.userName, queue, "h"+aDoctor.getCurrentPatient().getID()+Calendar.getInstance().getTime().getTime(),notes);
    }
    //get the absent patient details
    public void absentPatient(){
        aDoctor.absentPatient();
    }
    // Open the prescription
    public void openPrescription(String docName){
        new ShowPrescription(data.getPrescriptions(docName, aDoctor.currentPatient.getFirstName()+" "+aDoctor.currentPatient.getLastName(), aDoctor.currentPatient.getAge(), aDoctor.currentPatient.getID()),aDoctor.currentPatient.getAge()).setVisible(true);
    }
}
