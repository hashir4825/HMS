/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.models;

/**
 *
 * @author Student
 */
public class doctor {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    public int getId()
    {
        return id;
    }
    public void setId(int _id)
    {
        id=_id;
    }
    
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String _id)
    {
        firstName=_id;
    }
    
    public String getLastName()
    {
        return lastName;
    }
    public void setLasttName(String _id)
    {
        lastName=_id;
    }
    
    public int getAge()
    {
        return age;
    }
    public void setAge(int _id)
    {
        age=_id;
    }
    
}
