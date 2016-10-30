/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Doctor;

import main.Patient;
import main.MyQueue;

/**
 *
 * @author Yas
 */
public class Doctor {
    MyQueue<Patient> queue;
    String userName;
    Patient currentPatient;
    
    //Constructor for the doctor
    public Doctor(String userName){
        this.userName=userName;
        currentPatient=null;
        queue=null;
    }
    // assign doctor's queue
    void setQueue(MyQueue aQueue){
        queue=aQueue;
    }
    // call the next patient
    Patient nextPatient(){
        currentPatient=queue.dequeue();
        return currentPatient;
    }
    // Check the next patient
    Patient seeNextPatient(){
        return queue.peek();
    }
    // check if a patient is absent
    Patient absentPatient(){
        queue.enqueue(currentPatient);
        currentPatient=queue.dequeue();
        return currentPatient;
    }
    // get the remaining number of patients in the queue
    int getRemainingCount(){
        return queue.size();
    }
    // Get the deials of the current patient
    Patient getCurrentPatient(){
        return currentPatient;
    }
}
