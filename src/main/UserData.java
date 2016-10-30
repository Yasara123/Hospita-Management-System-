/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package main;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class UserData 
{           
    int next=1;
        String DATABASE_URL;
      Connection connection ;
      Statement statement ;
      ResultSet resultSet;
      public UserData(){
            DATABASE_URL = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql555819";
            connection = null;
            statement = null;
            resultSet = null;
            init();
      }
   public  void init( )
   {
 
      try
      {    Class.forName("com.mysql.jdbc.Driver"); 
         connection = DriverManager.getConnection( DATABASE_URL, "sql555819", "jM4*iJ8!" );
         statement = connection.createStatement();  


      }
      catch ( SQLException sqlException )                                
      {                                                                  
         sqlException.printStackTrace();
      } catch (ClassNotFoundException ex) {                                                 
           Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
       }    
      
      
                                       
   } 
 
   
   
   
}