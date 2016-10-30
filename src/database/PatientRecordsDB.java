/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package database;

import Doctor.Prescription;
import Doctor.prescriptionItem;
import main.Patient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.MyQueue;
import main.UserData;
 
//singleton the design pattern
public class PatientRecordsDB 
{
    private static PatientRecordsDB instance = new PatientRecordsDB();
    int next=1;
        String DATABASE_URL;
      Connection connection ;
      Statement statement ;
      ResultSet resultSet;
      
      //make the connection
      public PatientRecordsDB(){
            DATABASE_URL = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql260221";
            connection = null;
            statement = null;
            resultSet = null;
            init();
      }
   private  void init( )
   {
 
      try
      {    Class.forName("com.mysql.jdbc.Driver"); 
         connection = DriverManager.getConnection( DATABASE_URL, "sql260221", "hL2%nR8!" );
         statement = connection.createStatement();  

            resultSet = statement.executeQuery("select count(*) from PatientsRecords");

            if(resultSet.next()) {
            next=resultSet.getInt(1)+1;
            }
 
      }
      catch ( SQLException sqlException )                                
      {                                                                  
         sqlException.printStackTrace();
      } catch (ClassNotFoundException ex) {                                                 
           Logger.getLogger(PatientRecordsDB.class.getName()).log(Level.SEVERE, null, ex);
       }                                                 
                                          
   } 
   //inititalize the instance for the database
   public static PatientRecordsDB getInstance() {
      if(instance == null) {
         instance = new PatientRecordsDB();
      }
      return instance;
   }
   
   //initialize the patient object
   public Patient getPatient(String num){
            try {
                resultSet =  statement.executeQuery("SELECT * FROM PatientsRecords WHERE patient_Id=" +num);
               
                resultSet.first();
                return new Patient(resultSet.getObject(1).toString(),resultSet.getObject(2).toString(),resultSet.getObject(3).toString(),resultSet.getObject(4).toString(),resultSet.getObject(5).toString(),resultSet.getObject(6).toString(),resultSet.getObject(7).toString());
            } catch (SQLException ex) {
                Logger.getLogger(PatientRecordsDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
   }
   // ragister pation while adding row to the database
   public boolean registerPatient(Patient aPatient){
            try {
                statement.executeUpdate("INSERT INTO PatientsRecords (First_Name,  Last_Name,Age, NIC,Contact_No,Address) VALUES ('"+aPatient.getFirstName()+"','"+aPatient.getLastName()+"','"+aPatient.getAge()+"','"+aPatient.getNIC()+"','"+aPatient.getContactNo()+"','"+aPatient.getAddress()+"')");
                next++;
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(PatientRecordsDB.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            
            
   }
   //get the next patient ID
    public int getNextPatientID(){
       return next;
   }
   
    // create queue using patient iD and add to the pending list
    public MyQueue<String []> getmedList(String id){
       

        try {
            resultSet =  statement.executeQuery("SELECT * FROM  `pendingList` WHERE patientID=" +id+"");
            String [] temp=new String[2];
            resultSet.first();
            Statement st=connection.createStatement();
            ResultSet rs= st.executeQuery("SELECT * FROM  `"+resultSet.getObject(2).toString()+"` ");
            MyQueue<String []> queue=new MyQueue();
            while(rs.next()){
                temp=new String[2];
                temp[0]=rs.getObject(1).toString();
                temp[1]=rs.getObject(2).toString();
                queue.enqueue(temp);
            }
            return queue;
        } catch (SQLException ex) {
            Logger.getLogger(PatientRecordsDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
   }
    //check the passworks while accessing work profiles
    public boolean checkPassword(String userName,String password){
       String cmd="";
            try {
                resultSet =  statement.executeQuery("SELECT * FROM WorkerProfiles WHERE username='" +userName+"'");
               
                resultSet.first();
                
                if(!resultSet.getObject(5).toString().equals(password))
                    return false;
                resultSet.first();
                cmd=resultSet.getObject(6).toString();
                try                                                       
                {                                                         
                                   
                }                                        
                catch ( Exception exception )                              
                {                                                          
                   exception.printStackTrace();                            
                }   
                this.openGUI(cmd,userName);
                return true;
                } catch (SQLException ex) {
                Logger.getLogger(PatientRecordsDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            return false;
   }
    //open the user interface different professions
   public void openGUI(String type,String userName){
       switch(type){
           case "Doctor":{
               (new Doctor.welcomeFrame(userName)).setVisible(true);
               break;
           }
           case "Receptionist":{
               (new Receptionist.recept()).setVisible(true);
               break;
           }
           case "MLT":{
           try {
               new Lab.GUI().setVisible(true);
           } catch (SQLException ex) {
               Logger.getLogger(PatientRecordsDB.class.getName()).log(Level.SEVERE, null, ex);
           }
               break;
           }
           case "Pharmacist":{
               Pharmasist.phar1.getInstance().setVisible(true);
               break;
           }
           case "Manager":{
               new manager.Manager().setVisible(true);
               break;
           }
       } 
     
   }
   // get the patient from the database
       public MyQueue getDocQueue(String uName){
       try {
           Statement st=connection.createStatement();
                ResultSet rs1 =  st.executeQuery("SELECT patientID FROM appoinmentTabel WHERE `userName`='" +uName+"'");
                
                rs1.first();
                MyQueue<Patient> queue=new MyQueue();
                System.out.println(""+rs1.getObject(1).toString());
                queue.enqueue(this.getPatient(rs1.getObject(1).toString()));
                while ( rs1.next() ) 
                    {
                        System.out.println(""+rs1.getObject(1).toString());
                       queue.enqueue(this.getPatient(rs1.getObject(1).toString()));
                    }
                
                return queue;
            } catch (SQLException ex) {
                Logger.getLogger(PatientRecordsDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
   }
       
       // select the doctor
       public String [] getDoctorNames(){
           try {
                
                int a=0,i=0;
                ResultSet resultSet1 = statement.executeQuery("select count(*) from WorkerProfiles WHERE Profession='Doctor'");

            if(resultSet1.next()) {
            a=resultSet1.getInt(1);
            }
               System.out.println(""+a);
               ResultSet rs =  statement.executeQuery("SELECT * FROM WorkerProfiles WHERE Profession='Doctor'");
                rs.first();
                String [] names=new String[a];
                names[i++]="Dr. "+rs.getObject(2).toString()+" "+rs.getObject(3).toString();
                while ( rs.next() ) 
                    {
                        
                       names[i++]="Dr. "+rs.getObject(2).toString()+" "+rs.getObject(3).toString();
                    }
                
                return names;
            } catch (SQLException ex) {
                Logger.getLogger(PatientRecordsDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
       }
       
       //make appoinment for a doctor
       public boolean makeAppoinments(String docname1,String docName2,String PatientId,char time,Date aDate){
           try {
               resultSet=statement.executeQuery("SELECT UserName FROM WorkerProfiles WHERE   `FirstName` =  '"+docname1+"' AND  `SecondName` =  '"+docName2+"'");
               resultSet.first();
               statement.executeUpdate("INSERT INTO appoinmentTabel (userName,  PatientID,time, date) VALUES ('"+resultSet.getObject(1).toString()+"','"+PatientId+"','"+time+"','"+aDate.getYear()+":"+(1+aDate.getMonth())+":"+aDate.getDate()+"')");
                //next++;
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(PatientRecordsDB.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
       }
       //get the doctor name
       public String getDocName(String userName){
        try {
            resultSet=statement.executeQuery("SELECT * FROM WorkerProfiles WHERE   `UserName` =  '"+userName+"'");
          resultSet.first();
          return "Dr. "+resultSet.getObject(2).toString()+" "+resultSet.getObject(3).toString();
        } catch (SQLException ex) {
            Logger.getLogger(PatientRecordsDB.class.getName()).log(Level.SEVERE, null, ex);
        }
          
           return null;
       }
       
       //update the prescription 
       public boolean updatePrescription(String patientID,String docName,MyQueue<prescriptionItem> queue,String tabelName,String note){
               try {
                    Statement st=connection.createStatement();
                    st.execute("CREATE TABLE "+tabelName+"(medName  VARCHAR(30), noOfDays VARCHAR(5) )");
                    while (queue.hasItems()) {     
                        statement.executeUpdate("INSERT INTO `"+tabelName+"` (medName,  noOfDays) VALUES('"+queue.peek().getMedName()+"','"+queue.dequeue().getNoOfDays()+"')");
                    }
                    Statement st1=connection.createStatement();
                    st1.executeUpdate("INSERT INTO `pendingList` (patientID,  tableName) VALUES('"+patientID+"','"+tabelName+"')");
                    Statement st2=connection.createStatement();
                    st2.executeUpdate("INSERT INTO `patientsHistory` (patientID,  tabelName,docName,Date,notes) VALUES('"+patientID+"','"+tabelName+"','"+docName+"','"+(Calendar.getInstance().getTime().getYear()+1900)+":"+(1+Calendar.getInstance().getTime().getMonth())+":"+Calendar.getInstance().getTime().getDate()+"','"+note+"')");
                    return true;
               } catch (SQLException ex) {
                   Logger.getLogger(PatientRecordsDB.class.getName()).log(Level.SEVERE, null, ex);
               }
              return false;
       }
       
       //get the prescription
       public MyQueue<Prescription> getPrescriptions(String docName,String patientName,String patientAge,String patientID){
           Statement st;
        try {
            st = connection.createStatement();
           ResultSet rs=st.executeQuery("SELECT tabelName,notes,Date FROM patientsHistory WHERE   `patientID` =  '"+patientID+"'");
           MyQueue<Prescription> queue= new MyQueue<>();
           String str="";
           while(rs.next()){
               resultSet=statement.executeQuery("SELECT * FROM "+rs.getObject(1).toString()+" ");
               while(resultSet.next()){
                   str="\n\t";
                   str=str.concat(resultSet.getObject(1).toString()+"  for  "+resultSet.getObject(2).toString()+" days\n");
                   
               }
               queue.enqueue(new Prescription(docName,patientName,rs.getDate(3).toString(),str,rs.getString(2)) );
           }
           return queue;
        } catch (SQLException ex) {
            Logger.getLogger(PatientRecordsDB.class.getName()).log(Level.SEVERE, null, ex);
        }
           return null;
       }
   
}