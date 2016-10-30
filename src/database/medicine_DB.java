package database;



import Pharmasist.medicine;
import Pharmasist.medicine;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
 
public class medicine_DB 
{   
    private static medicine_DB instance=new medicine_DB();              //singleton the database
    int next=1;
      String DATABASE_URL;
      Connection connection ;
      Statement statement ;
      ResultSet resultSet;
      
      //make the connection
      public medicine_DB(){
            DATABASE_URL = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql555846";      
            connection = null;
            statement = null;
            resultSet = null;
            init();
      }
      //Implement singleton pattern
      public static medicine_DB getInstance(){
          if(instance==null)
              instance=new medicine_DB();
          return instance;
      }
   public  void init( )
   {
 
      try
      {    Class.forName("com.mysql.jdbc.Driver"); 
         connection = DriverManager.getConnection( DATABASE_URL, "sql555846", "rY3%nK7*" );
         statement = connection.createStatement();                                  
         
         
            resultSet = statement.executeQuery("select count(*) from `medicine_stock`");

            if(resultSet.next()) {
            next=resultSet.getInt(1);
            }

      }
      catch ( SQLException sqlException )                                
      {                              
          try {
              
              System.out.println("vhvhvh15151113");
              statement.execute("CREATE TABLE medicine_stock( \n" +             //creating a table
                      "    Med_ID INT PRIMARY KEY AUTO_INCREMENT,\n" +
                      "    Name VARCHAR (100)NOT NULL,\n" +
                      "	Quantity INT ,\n" +
                      "    Price FLOAT,\n" +
                      "    Dealer_Detail VARCHAR (100) NOT NULL\n" +
                      "    )");
              init();
          } catch (SQLException ex) {
              Logger.getLogger(medicine_DB.class.getName()).log(Level.SEVERE, null, ex);
          }
              sqlException.printStackTrace();
         
      } catch (ClassNotFoundException ex) {                                                 
           Logger.getLogger(medicine_DB.class.getName()).log(Level.SEVERE, null, ex);
           
       }                                                 
                                          
   } 
   //get initialize the medicine
   medicine getMedicine(String num){
            try {
                resultSet =  statement.executeQuery("SELECT * FROM `medicine_stock` WHERE Med_ID=" +num);
               
                resultSet.first();
                return new medicine(Integer.parseInt(resultSet.getObject(1).toString()),resultSet.getObject(2).toString(),Integer.parseInt(resultSet.getObject(3).toString()),Float.parseFloat(resultSet.getObject(4).toString()),resultSet.getObject(5).toString());
            } catch (SQLException ex) {
                Logger.getLogger(medicine_DB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
   }
   
   // get the quantity of particular medicine by patient ID when medicine new stock arrives
   public int getquantity(int num){     
            try {
                ResultSet rs;
                Statement sst=connection.createStatement();
                rs =  sst.executeQuery("SELECT * FROM `medicine_stock` WHERE  Med_ID=" +(num+1));
               
                rs.first();                
                return (Integer.parseInt(rs.getObject(3).toString()));
            } catch (SQLException ex) {
                Logger.getLogger(medicine_DB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 0;
   }
   
   // get the quantity of particular medicine by name
   public int getquantityByname(String medName){    
            try {
                resultSet =  statement.executeQuery("SELECT * FROM `medicine_stock` WHERE `Name` ='" +medName+"'");                
                resultSet.first();                
                return (Integer.parseInt(resultSet.getObject(3).toString()));
            } catch (SQLException ex) {
                Logger.getLogger(medicine_DB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 0;
   }
   
   //decrease the amount of medicine after submitting
   public float getPriceByname(String medName){    
            try {
                resultSet =  statement.executeQuery("SELECT * FROM `medicine_stock` WHERE `Name` ='" +medName+"'");
                //System.out.println(resultSet.getObject(3).toString());
                resultSet.first();
                System.out.println(resultSet.getObject(4).toString());
                return (Float.parseFloat(resultSet.getObject(4).toString()));
            } catch (SQLException ex) {
                Logger.getLogger(medicine_DB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 0;
   }
   
   
   //initialize the medicine,, insert the row to the medicine table
   public boolean initializeMedicine(medicine aMedicine){
            try {
                statement.executeUpdate("INSERT INTO medicine_stock (Name,Quantity,Price,Dealer_Detail) VALUES ('"+aMedicine.getMedName()+"','"+aMedicine.getQuantity()+"','"+aMedicine.getPrice()+"','"+aMedicine.getDealer_Details()+"')");
                next++;
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(medicine_DB.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            
            
   }
   // update quantity of database when new stock arrives 
   public boolean updateQuantityMedicine(int medID,int qty){    
            try {
                int quantity = getquantity(medID);                
                Statement st=connection.createStatement();
                quantity+=qty;                
                st.executeUpdate("UPDATE `medicine_stock` SET `Quantity`="+quantity+" WHERE `Med_ID` ="+(medID+1));
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(medicine_DB.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            
            
   }
   // update quantity of database after submiting
   public boolean updateDatabaseAfterSubmitting(String medName, int qty){    
            try {
                int quantity=getquantityByname(medName);
                quantity-=qty;
                if(quantity>50){
                statement.executeUpdate("UPDATE `medicine_stock` SET `Quantity`="+quantity+" WHERE `Name` ='" +medName+"'");
                return true;
                }
                else{
                    JOptionPane.showMessageDialog(null, "Medicine quantity is low. New stock ordered");
                    statement.executeUpdate("UPDATE `medicine_stock` SET `Quantity`="+quantity+" WHERE `Name` ='" +medName+"'");
                    //reoder the medicine.
                    return true;
                }
                    
            } catch (SQLException ex) {
                Logger.getLogger(medicine_DB.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
   }
   
   public boolean setBill(String patientID,String Name,String bill){
       try {
                statement.executeUpdate("INSERT INTO `patient_pharmacy_bill` (patient,name,bill) VALUES ('"+patientID+"','"+Name+"','"+bill+"')");
                next++;
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(medicine_DB.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
   }
   
   
   public String [] getStringArray(){
       String [] list1 = null;
        try {
            
         int a=0;
         resultSet = statement.executeQuery("select count(*) from `medicine_stock`");

            if(resultSet.next()) {
            next=resultSet.getInt(1);
            }
         list1=new String[next];

         resultSet = statement.executeQuery("select * from `medicine_stock`");
         while ( resultSet.next() ) 
         {
             list1[a]=resultSet.getObject( 2 ).toString();
            
            a++;
         } 
        } catch (SQLException ex) {
            Logger.getLogger(medicine_DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list1;
   }
     int getNextID(){
       return next;
   }
   
   
   
   
}