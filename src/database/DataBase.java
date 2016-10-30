/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package database;


import employee.Employee;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

 
public class DataBase 
{
    private static DataBase instance = null;
    int next=1;
        String DATABASE_URL;
      Connection connection ;
      Statement statement ;
      ResultSet resultSet;
      //make the connection
      public DataBase(){
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
           Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
       }                                                 
                                          
   } 
   
   public static DataBase getInstance() {
      if(instance == null) {
         instance = new DataBase();
      }
      return instance;
   }
   
    public boolean checkPassword(String userName,String password){
       String cmd="";
            try {
                resultSet =  statement.executeQuery("SELECT * FROM login WHERE username='" +userName+"'");
               
                resultSet.first();
                if(!resultSet.getObject(4).toString().equals(password))
                    return false;
                resultSet.first();
                cmd=resultSet.getObject(1).toString();
                try                                                       
                {                                       
                }                                        
                catch ( Exception exception )                              
                {                                                          
                   exception.printStackTrace();                            
                }   

                return true;
                } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
   }
   public boolean AddEmployee(Employee employee){
       try {
                statement.executeUpdate("INSERT INTO WorkerProfiles(NIC,FirstName, SecondName, Username, Password, Profession) VALUES ('"+employee.getNIC()+"','"+employee.getFirstname()+"','"+employee.getSecondname()+"','"+employee.getUsername()+"','"+employee.getPassword()+"','"+employee.getProfession()+"')");
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
   }
   public Employee SearchEmployee(Employee employee){
            try {
                resultSet =  statement.executeQuery("SELECT * FROM WorkerProfiles WHERE NIC='" +employee.getNIC()+"'");
                resultSet.first();
                employee.setFirstname(resultSet.getObject(2).toString());
                employee.setSecondname(resultSet.getObject(3).toString());
                employee.setProfession(resultSet.getObject(6).toString());
                return employee;
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            
     
   }
   public boolean RemoveEmployee(Employee employee){
        try {
                statement.executeUpdate("DELETE FROM `sql555819`.`WorkerProfiles` WHERE `WorkerProfiles`.`NIC` = '"+employee.getNIC()+"'");
                return true;
        } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
                return false;
        }
   }
   public boolean AddDiscount(String type, float discount){
        try {
              statement.executeUpdate("UPDATE DiscountTable SET Discount = '"+discount+"' WHERE PatientType = '"+type+"'");
              return true;
          } catch (SQLException ex) {
              Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
              return false;
          }
   }
   public float getIncomeDetails(){
         try {
                resultSet = statement.executeQuery("SELECT * FROM `FinancialDetails`");
                resultSet.first();
                return resultSet.getFloat(1);
        } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
                return 0;
        }
   }
   public float[] getDiscountDetails(){
       float discount[] = new float[3];
        try {
                resultSet = statement.executeQuery("SELECT Discount FROM DiscountTable");
                resultSet.first();
                discount[0] = resultSet.getFloat(1);
                resultSet.next();
                discount[1] = resultSet.getFloat(1);
                resultSet.next();
                discount[2] = resultSet.getFloat(1);
                return discount;
        } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
                return null;
        }
   }
   
}