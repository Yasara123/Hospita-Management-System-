/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Lab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hasangi Thathsarani
 */
public class PatientRecords_DB {
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/LabDatabase";
   public static void main( String args[] )
   {
      Connection connection = null;
      Statement statement = null;
      ResultSet resultSet = null;
 
      try
      {    Class.forName("com.mysql.jdbc.Driver"); 
         connection = DriverManager.getConnection( DATABASE_URL, "root", "" );
         statement = connection.createStatement();                                  
         resultSet = statement.executeQuery("SELECT * FROM PatientHistory" );
         ResultSetMetaData metaData = resultSet.getMetaData();
         int numberOfColumns = metaData.getColumnCount();     
         System.out.println( "atientdetails in the  Database:\n" );
          
         for ( int i = 1; i <= numberOfColumns; i++ )
            System.out.printf( "%-8s\t", metaData.getColumnName( i ) );
         System.out.println();
          
         while ( resultSet.next() ) 
         {
            for ( int i = 1; i <= numberOfColumns; i++ )
               System.out.printf( "%-8s\t", resultSet.getObject( i ) );
            System.out.println();
         } 
      }
      catch ( SQLException sqlException )                                
      {                                                                  
         sqlException.printStackTrace();
      } catch (ClassNotFoundException ex) {                                                 
           Logger.getLogger(PatientRecords_DB.class.getName()).log(Level.SEVERE, null, ex);
       }                                                 
      finally
      {                                                             
         try                                                       
         {                                                          
            resultSet.close();                                      
            statement.close();                                      
            connection.close();                                     
         }                                        
         catch ( Exception exception )                              
         {                                                          
            exception.printStackTrace();                            
         }                                        
      }                                           
   } 
}
