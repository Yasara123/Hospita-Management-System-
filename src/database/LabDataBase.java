/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package database;

//import Receptionist.Patient;
import Lab.AddTests;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
//import main.UserData;
 
public class LabDataBase 
{
    //singleton the design pattern
    private static LabDataBase instance = null;
    int next=1;
        String DATABASE_URL;
      Connection connection ;
      Statement statement ;
      ResultSet resultSet;
      public LabDataBase(){
            DATABASE_URL = "jdbc:mysql://sql4.freesqldatabase.com:3306/sql456044";
            connection = null;
            statement = null;
            resultSet = null;
            init();
      }
   private  void init( )
   {
             try {
                       Class.forName("com.mysql.jdbc.Driver");
            connection=DriverManager.getConnection(DATABASE_URL, "sql456044", "lY7!sC7!");
             statement = connection.createStatement();
                  } 
             catch (ClassNotFoundException ex) {
            Logger.getLogger(AddTests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddTests.class.getName()).log(Level.SEVERE, null, ex);
        }
              
                                          
   } 
   
   public static LabDataBase getInstance() {
      if(instance == null) {
         instance = new LabDataBase();
      }
      return instance;
   }
   public boolean insertTestName(String text,double bill){
        try {
            statement.executeUpdate("INSERT INTO `testdescription` ( `TestName`, `BillValue`) VALUES ('"+text+"', '"+bill+"')");
             return true;
        } catch (SQLException ex) {
            Logger.getLogger(LabDataBase.class.getName()).log(Level.SEVERE, null, ex);
             return false;
        }
          } 
   
   public Vector DisplayDataComboBox() throws SQLException{
         String query="SELECT * FROM stock";
         int i=0;
         String chemName;
          resultSet = statement.executeQuery(query);
          Vector<String> data = new Vector<>();
                      while(resultSet.next()){
              data.add(resultSet.getString("ChemicalName"));
                       
           }
        return data;
       }
     public Vector DisplayTestComboBox() throws SQLException {
         String query="SELECT * FROM testDescription";
              resultSet = statement.executeQuery(query);
          Vector<String> data = new Vector<>();
                      while(resultSet.next()){
              data.add(resultSet.getString("TestName"));
                       
           }
        return data;
        
       }
   public boolean createTestTables(String testName){
           String query=("CREATE TABLE " +testName+ " (ChemicalName VARCHAR(50), Quantity FLOAT)");
                
        try {
            statement.execute(query);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LabDataBase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
                 }
   public boolean insertDataNewTest(String testName,String ChemName,double Quantity){
       
      String query= ("INSERT INTO `"+testName+"` (`ChemicalName`, `Quantity`) VALUES ('"+ChemName+"', '"+Quantity+"');");
        try {
             statement.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LabDataBase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
       
       
   }
   public int getTestNo(String testName){
    int testNo=0;
        String query="(SELECT TestNo From testDescription WHERE TestName = '"+testName+"')";
        try {
            resultSet=statement.executeQuery(query);
             resultSet.first();
         testNo=Integer.parseInt(resultSet.getObject(1).toString());
                     System.out.println(testNo);
        } catch (SQLException ex) {
            Logger.getLogger(LabDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    return testNo;
}
   public boolean insertToPatientHistory(int id,String testName) {
                      int testID=getTestNo(testName);
         DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
       Date date = new Date();
        String query="INSERT INTO patientHistory (PatientID,TestName,Date,TestID) VALUES ('"+id+"','"+testName+"','"+dateFormat.format(date)+"','"+testID+"')";
        try {
            statement.executeUpdate(query);
             return true;
        } catch (SQLException ex) {
            Logger.getLogger(LabDataBase.class.getName()).log(Level.SEVERE, null, ex);
             return false;
        }
           }
   public boolean updatePatientHistory(int id,String name){
        String query="update patientHistory SET TestResults ='"+name+"' WHERE PatientID = '"+id+"'";
        try {
            statement.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LabDataBase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
   }
   public boolean stockDecrease(String testName){
       ResultSet resultSettemp;
        try {
       Statement statementtemp=connection.createStatement();
       Statement stat = connection.createStatement();
       String querytemp = "(SELECT * FROM "+testName+")";
       double newquantity=0;
       double quantity=0;
                  resultSet = statement.executeQuery(querytemp);
            
            while(resultSet.next()){
                newquantity = Double.parseDouble(resultSet.getObject(2).toString());
         String query = "(SELECT QuantityAvailable FROM stock WHERE ChemicalName = '"+resultSet.getObject(1).toString()+"')";
          resultSettemp=statementtemp.executeQuery(query);
        resultSettemp.first();
        quantity= Double.parseDouble(resultSettemp.getObject(1).toString()) - newquantity;
         String querytem="update stock SET QuantityAvailable ='"+quantity+"' WHERE ChemicalName = '"+resultSet.getObject(1)+"'";
             stat.executeUpdate(querytem);
                           }
         return true;
        } catch (SQLException ex) {
            Logger.getLogger(LabDataBase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }   
   }
   public Vector checkStockLimits(){
                 String query = "(SELECT * FROM stock)";
        try {
            resultSet = statement.executeQuery(query);
            Vector<Double> data = new Vector<>();
                      while(resultSet.next()){
              data.add(resultSet.getDouble("QuantityAvailable"));       
           } return data;
        } catch (SQLException ex) {
            Logger.getLogger(LabDataBase.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
           }
   public boolean updateStockTable(String str,String quantity){
       double quantitytemp=0; 
       quantitytemp= Double.parseDouble(quantity);
        String querytemp = "(SELECT QuantityAvailable FROM stock WHERE ChemicalName = '"+str+"')";
        try {
                    resultSet=statement.executeQuery(querytemp);
        resultSet.first();
        quantitytemp+= Double.parseDouble(resultSet.getObject(1).toString()); 
                String query="update stock SET QuantityAvailable ='"+quantitytemp+"' WHERE ChemicalName = '"+str+"'";
             statement.executeUpdate(query); 
            System.out.println(quantitytemp);
        return true;
        } catch (SQLException ex) {
            Logger.getLogger(LabDataBase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
   }
   public  DefaultTableModel showStocks(){
        try {
            resultSet = statement.executeQuery("SELECT * FROM stock" );
           return myTableModelForStocks(resultSet);
            
                   } catch (SQLException ex) {
            Logger.getLogger(LabDataBase.class.getName()).log(Level.SEVERE, null, ex);
            return null;
          
        }
          
   }
   public static DefaultTableModel myTableModelForStocks(ResultSet rs) throws SQLException {
        ResultSetMetaData metadata = (ResultSetMetaData) rs.getMetaData();
        int columnsCount = metadata.getColumnCount();
        Vector<String> columnNames = new Vector<>();
        for (int i = 1; i < 4; i++) {
            columnNames.add(metadata.getColumnName(i));
        }
        Vector<Object> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> eachLine = new Vector<>();
            for (int i = 1; i < 4; i++) {
                eachLine.add(rs.getObject(i));
            }
            data.add(eachLine);
        }
        return new DefaultTableModel(data, columnNames);

    }
    public  DefaultTableModel showSuppliers(){
        try {
            resultSet = statement.executeQuery("SELECT * FROM stock" );
           return myTableModelForSuppliers(resultSet);
            
                   } catch (SQLException ex) {
            Logger.getLogger(LabDataBase.class.getName()).log(Level.SEVERE, null, ex);
            return null;
                  }  
   }
    public static DefaultTableModel myTableModelForSuppliers(ResultSet rs) throws SQLException {
        ResultSetMetaData metadata = (ResultSetMetaData) rs.getMetaData();
        int columnsCount = metadata.getColumnCount();
        Vector<String> columnNames = new Vector<>();
        for (int i = 1; i <= columnsCount; i++) {
            if(!metadata.getColumnName(i).contains("Quantity"))
            columnNames.add(metadata.getColumnName(i));
        }
        Vector<Object> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> eachLine = new Vector<>();
            for (int i = 1; i <= columnsCount; i++) {
                if(!metadata.getColumnName(i).contains("Quantity"))
                eachLine.add(rs.getObject(i));
            }
            data.add(eachLine);
        }
        return new DefaultTableModel(data, columnNames);

    }
    public  DefaultTableModel showTests(){
        try {
            resultSet = statement.executeQuery("SELECT * FROM TestDescription" );
           return myTableModelForTests(resultSet);
            
                   } catch (SQLException ex) {
            Logger.getLogger(LabDataBase.class.getName()).log(Level.SEVERE, null, ex);
            return null;
                  }  
   }
public static DefaultTableModel myTableModelForTests(ResultSet rs) throws SQLException {
        ResultSetMetaData metadata = (ResultSetMetaData) rs.getMetaData();
        int columnsCount = metadata.getColumnCount();
        Vector<String> columnNames = new Vector<>();
        for (int i = 1; i <= columnsCount; i++) {
            columnNames.add(metadata.getColumnName(i));
            System.out.println(columnNames);
        }
        Vector<Object> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> eachLine = new Vector<>();
            for (int i = 1; i <= columnsCount; i++) {
                eachLine.add(rs.getObject(i));
            }
            data.add(eachLine);
        }
        return new DefaultTableModel(data, columnNames);

    }
 public boolean createTestTemp(int patientID){
        String query=("CREATE TABLE test" +patientID+ " (Content VARCHAR(50), Result FLOAT)");
         try {
            statement.execute(query);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LabDataBase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
 public Vector DisplayTestsCombo(int patientID){
         String query="(SELECT TestName FROM patientHistory WHERE patientID = '"+patientID+"')";
         int i=0;
              try {
            //String[] strArray = null;
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(LabDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
          Vector<String> data = new Vector<>();
        try {
            while(resultSet.next()){
                data.add(resultSet.getString("TestName"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(LabDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
       }
 public boolean insertTestResults(int patientID,String content,double result){
       PreparedStatement preparedStatement;
      String query= ("INSERT INTO test" +patientID+ " (`Content`, `Result`) VALUES ('"+content+"', '"+result+"');");
        try {
             statement.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LabDataBase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
       
       
   }
 public boolean insertNewChemical(String chemName,double quantity,String supName,String suptele,String supAdd,String supEmail){
     //inserts new chemical values in to the stock table
      String query= "INSERT INTO stock (ChemicalName,QuantityAvailable,SupplierName,SupplierTele,SupplierAddress,SupplierEmail) VALUES ('"+chemName+"','"+quantity+"','"+supName+"','"+suptele+"','"+supAdd+"','"+supEmail+"')";
        try {
             statement.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
 }

    }
