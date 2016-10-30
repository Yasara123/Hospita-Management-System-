/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package employee;

/**
 *
 * @author User
 */
public class Employee {
    private String firstname;
    private String secondname;
    private String NIC;
    private String profession;
    private String username;
    private String password;
    
      public String getFirstname(){
         return firstname;
     }
     public String getSecondname(){
         return secondname;
     }
     public String getNIC(){
         return NIC;
     }
     public String getProfession(){
         return profession;
     }
     public String getUsername(){
         return  username;
     }
     public  String getPassword(){
         return password;
     }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @param secondname the secondname to set
     */
    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    /**
     * @param NIC the NIC to set
     */
    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    /**
     * @param profession the profession to set
     */
    public void setProfession(String profession) {
        this.profession = profession;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
}
