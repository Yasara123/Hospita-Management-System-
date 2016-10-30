
package manager;
 import employee.Employee;
import database.DataBase;
/**
 *
 * @author User
 */
public class ManagerController {
    //Making the ManagerConroller singleton
    private static ManagerController instance=null;
    Employee employee = new Employee();
    DataBase database = DataBase.getInstance();
    public static ManagerController getInstance(){
        if(instance==null ){
            instance = new ManagerController();
        }
        return instance;
    }
    // Add employee to the database
    public void AddEmployee(){
        database.AddEmployee(employee);
    }
    // SearhEmployee from the database
    public Employee SearchEmployee(Employee employee){
        employee=database.SearchEmployee(employee);
        return employee;
    }
    // Remove employee from the database
    public void RemoveEmployee(){
        database.RemoveEmployee(employee);
    }
    // Set discount database
    public void setDiscount(String type,float discount){
        database.AddDiscount(type,discount);
    }
    // view current discount details
    public float[] viewDiscount(){
        return database.getDiscountDetails();
    }
    // view income details from the database
    public float ViewIncome(){
        return database.getIncomeDetails();
    }
   
   
}
