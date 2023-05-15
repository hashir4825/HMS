/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hms;

import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DatabaseConnection {

    static Connection conn;

    DatabaseConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/first_hms_database", "root", "");
            System.out.println("Connected to the database");
            createEmployeeTable();
            createReceptionistDetailTable();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    // Function to insert data into a table with six columns

    public static void insertData(String Staff_ID, String Name, String CNIC, String Gender, String Age, String Staff_type) throws SQLException {
        // Create a PreparedStatement object to execute SQL queries with parameters
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Employee_Table (Staff_ID, Name, cnic, Gender,age,staff_type) VALUES (?, ?, ?, ?, ?, ?)");

        // Set the values of the parameters in the PreparedStatement object
        stmt.setString(1, Staff_ID);
        stmt.setString(2, Name);
        stmt.setString(3, CNIC);
        stmt.setString(4, Gender);
        stmt.setString(5, Age);
        stmt.setString(6, Staff_type);

        // Execute the SQL INSERT statement
        int rowsInserted = stmt.executeUpdate();
        System.out.println(rowsInserted + " row(s) inserted");
        // Close the PreparedStatement and Connection objects
        stmt.close();
    }

    public static void createEmployeeTable() throws SQLException {
        // Create a Statement object to execute SQL queries
        Statement stmt = conn.createStatement();

        // Define the SQL CREATE TABLE statement
        String createTableSql = "CREATE TABLE if not exists Employee_Table ("
                + "staff_id varchar(11),"
                + "name varchar(50),"
                + "cnic varchar(15),"
                + "gender varchar(15),"
                + "age varchar(11),"
                + "staff_type varchar(20),"
                + "PRIMARY KEY (staff_id)"
                + ")";

        // Execute the SQL CREATE TABLE statement
        stmt.executeUpdate(createTableSql);
        System.out.println("Employee_Table created");

        // Close the Statement object
        stmt.close();
    }

    public static void displayEmployeeTable(JTable table) throws SQLException {
    // Create a Statement object to execute SQL queries
    Statement stmt = conn.createStatement();

    // Define the SQL SELECT statement
    String selectSql = "SELECT * FROM Employee_Table";

    // Execute the SQL SELECT statement and retrieve the result set
    ResultSet rs = stmt.executeQuery(selectSql);

    // Create a DefaultTableModel object to hold the query results
    DefaultTableModel tableModel = new DefaultTableModel();

    // Get the metadata for the ResultSet and add the column names to the table model
    ResultSetMetaData rsmd = rs.getMetaData();
    int numColumns = rsmd.getColumnCount();
    for (int i = 1; i <= numColumns; i++) {
        tableModel.addColumn(rsmd.getColumnLabel(i));
    }

    // Add the rows from the ResultSet to the table model
    while (rs.next()) {
        Object[] rowData = new Object[numColumns];
        for (int i = 1; i <= numColumns; i++) {
            rowData[i-1] = rs.getObject(i);
        }
        tableModel.addRow(rowData);
    }

    // Set the table model on the JTable component
    table.setModel(tableModel);

    // Close the ResultSet and Statement objects
    rs.close();
    stmt.close();
}

    public static void deleteSelectedRow(JTable employeeTable) throws SQLException {
    int selectedRow = employeeTable.getSelectedRow();
    if (selectedRow == -1) {
        // No row is selected
        return;
    }
    
    String staffId = employeeTable.getModel().getValueAt(selectedRow, 0).toString();

    // Create a Statement object to execute SQL queries
    Statement stmt = conn.createStatement();

    // Define the SQL DELETE statement
    String deleteSql = "DELETE FROM Employee_Table WHERE staff_id = '" + staffId + "'";

    // Execute the SQL DELETE statement
    int rowCount = stmt.executeUpdate(deleteSql);
    System.out.println(rowCount + " row(s) deleted");

    // Close the Statement object
    stmt.close();
}

public static void updateEmployeeTable(JTable table) throws SQLException {
    // Get the selected row in the JTable
    int selectedRow = table.getSelectedRow();

    // Get the values of the columns in the selected row
    String staffId = table.getValueAt(selectedRow, 0).toString();
    String name = table.getValueAt(selectedRow, 1).toString();
    String cnic = table.getValueAt(selectedRow, 2).toString();
    String gender = table.getValueAt(selectedRow, 3).toString();
    String age = table.getValueAt(selectedRow, 4).toString();
    String staffType = table.getValueAt(selectedRow, 5).toString();

    // Create a PreparedStatement object to execute parameterized SQL queries
    PreparedStatement pstmt = conn.prepareStatement("UPDATE Employee_Table SET name=?, cnic=?, gender=?, age=?, staff_type=? WHERE staff_id=?");

    // Set the parameter values for the PreparedStatement
    pstmt.setString(1, name);
    pstmt.setString(2, cnic);
    pstmt.setString(3, gender);
    pstmt.setString(4, age);
    pstmt.setString(5, staffType);
    pstmt.setString(6, staffId);

    // Execute the PreparedStatement
    int rowCount = pstmt.executeUpdate();
    
    // Check if the update was successful
    if (rowCount > 0) {
        System.out.println(rowCount + " row(s) updated successfully");
    } else {
        System.out.println("Failed to update the row");
    }

    // Close the PreparedStatement object
    pstmt.close();
}


// queries for receptionist detail table

//create table query
public static void createReceptionistDetailTable() throws SQLException {
    // Create a Statement object for sending SQL statements to the database
    Statement stmt = conn.createStatement();

    // Define the SQL statement for creating the table
    String sql = "CREATE TABLE if not exists receptionistdetail (" +
                 "name VARCHAR(50)," +
                 "cnic VARCHAR(15)," +
                 "id VARCHAR(20)," +
                 "gender VARCHAR(10)," +
                 "username VARCHAR(20)," +
                 "password VARCHAR(20)," +
                 "age VARCHAR(3))";

    // Execute the SQL statement to create the table
    stmt.executeUpdate(sql);
    System.out.println("Receptionist created");


    // Close the Statement object
    stmt.close();
}

//insert query 
public static void insertIntoReceptionistDetailTable(String name, String cnic, String id, String gender, String username, String password, String age) throws SQLException {
    // Create a PreparedStatement object to execute parameterized SQL queries
    PreparedStatement pstmt = conn.prepareStatement("INSERT INTO receptionistdetail (name, cnic, id, gender, username, password, age) VALUES (?, ?, ?, ?, ?, ?, ?)");

    // Set the parameter values for the PreparedStatement
    pstmt.setString(1, name);
    pstmt.setString(2, cnic);
    pstmt.setString(3, id);
    pstmt.setString(4, gender);
    pstmt.setString(5, username);
    pstmt.setString(6, password);
    pstmt.setString(7, age);

    // Execute the PreparedStatement
    int rowCount = pstmt.executeUpdate();
    System.out.println(rowCount + " row(s) inserted");

    // Close the PreparedStatement object
    pstmt.close();
}

// display query of receptionist table 
public static void displayReceptionistTable(JTable table) throws SQLException {
    // Create a Statement object for sending SQL statements to the database
    Statement stmt = conn.createStatement();

    // Define the SQL statement for selecting all records from the receptionistdetail table
    String sql = "SELECT * FROM receptionistdetail";

    // Execute the SQL statement and get the ResultSet object
    ResultSet rs = stmt.executeQuery(sql);

    // Get the ResultSetMetaData object to get the number of columns in the result set
    ResultSetMetaData rsmd = rs.getMetaData();
    int numColumns = rsmd.getColumnCount();

    // Create a new DefaultTableModel with the column names from the ResultSetMetaData object
    DefaultTableModel model = new DefaultTableModel();
    for (int i = 1; i <= numColumns; i++) {
        model.addColumn(rsmd.getColumnName(i));
    }

    // Add each row of the ResultSet to the DefaultTableModel
    while (rs.next()) {
        Object[] rowData = new Object[numColumns];
        for (int i = 1; i <= numColumns; i++) {
            rowData[i-1] = rs.getObject(i);
        }
        model.addRow(rowData);
    }

    // Set the DefaultTableModel as the model for the JTable
    table.setModel(model);

    // Close the ResultSet and Statement objects
    rs.close();
    stmt.close();
}

// delete query for receptionist table
public static void deleteRecordFromTable(JTable table) throws SQLException {
    // Get the selected row in the JTable
    int selectedRow = table.getSelectedRow();

    // Get the staff ID of the selected row
    
    //the value of column is 2 bcz column no 2 is staffid from the rtable and columns start from 0 index 
    String staffId = table.getValueAt(selectedRow, 2).toString();

    // Create a PreparedStatement object to execute parameterized SQL queries
    PreparedStatement pstmt = conn.prepareStatement("DELETE FROM receptionistdetail WHERE id=?");

    // Set the parameter value for the PreparedStatement
    pstmt.setString(1, staffId);

    // Execute the PreparedStatement
    int rowCount = pstmt.executeUpdate();
    System.out.println(rowCount + " row(s) deleted");

    // Close the PreparedStatement object
    pstmt.close();
}

// update query for rceptionist table
public static void updateReceptionistTable(JTable table) throws SQLException {
    // Get the selected row in the JTable
    int selectedRow = table.getSelectedRow();

    // Get the values of the columns in the selected row
    String id = table.getValueAt(selectedRow, 2).toString();
    String name = table.getValueAt(selectedRow, 0).toString();
    String cnic = table.getValueAt(selectedRow, 1).toString();
    String gender = table.getValueAt(selectedRow, 3).toString();
    String username = table.getValueAt(selectedRow, 4).toString();
    String password = table.getValueAt(selectedRow, 5).toString();
    String age = table.getValueAt(selectedRow, 6).toString();

    // Create a PreparedStatement object to execute parameterized SQL queries
    PreparedStatement pstmt = conn.prepareStatement("UPDATE receptionistdetail SET name=?, cnic=?, gender=?, username=?, password=?, age=? WHERE id=?");

    // Set the parameter values for the PreparedStatement
    pstmt.setString(1, name);
    pstmt.setString(2, cnic);
    pstmt.setString(3, gender);
    pstmt.setString(4, username);
    pstmt.setString(5, password);
    pstmt.setString(6, age);
    pstmt.setString(7, id);

    // Execute the PreparedStatement
    int rowCount = pstmt.executeUpdate();
    System.out.println(rowCount + " row(s) updated");

    // Close the PreparedStatement object
    pstmt.close();
}



//here begins the dsoctortable 

//create table query
public static void createDoctorTable() throws SQLException {
    // Create a Statement object for sending SQL statements to the database
    Statement stmt = conn.createStatement();

    // Define the SQL statement for creating the doctortable
    String sql = "CREATE TABLE IF NOT EXISTS doctortable ("
            + "id varchar(44),"
            + "name VARCHAR(50),"
            + "username VARCHAR(20),"
            + "cnic VARCHAR(15),"
            + "gender VARCHAR(10),"
            + "age varchar(33),"
            + "password VARCHAR(20),"
            + "department VARCHAR(50)"
            + ")";

    // Execute the SQL statement to create the doctortable
    stmt.executeUpdate(sql);
    System.out.println("DoctorTable created");

    // Close the Statement object
    stmt.close();
}

//insert into doctor table
public static void insertIntoDoctorTable(String id, String name, String username, String cnic, String gender, String age, String password, String department) throws SQLException {
    // Create a PreparedStatement object to execute parameterized SQL queries
    PreparedStatement pstmt = conn.prepareStatement("INSERT INTO doctortable (id, name, username, cnic, gender, age, password, department) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

    // Set the parameter values for the PreparedStatement
    pstmt.setString(1, id);
    pstmt.setString(2, name);
    pstmt.setString(3, username);
    pstmt.setString(4, cnic);
    pstmt.setString(5, gender);
    pstmt.setString(6, age);
    pstmt.setString(7, password);
    pstmt.setString(8, department);

    // Execute the PreparedStatement
    int rowCount = pstmt.executeUpdate();
    System.out.println(rowCount + " row(s) inserted");

    // Close the PreparedStatement object
    pstmt.close();
}


//display doctor table query
public static void displayDoctorTable(JTable table) throws SQLException {
    // Create a Statement object to execute SQL queries
    Statement stmt = conn.createStatement();

    // Define the SQL SELECT statement
    String selectSql = "SELECT * FROM doctortable";

    // Execute the SQL SELECT statement
    ResultSet rs = stmt.executeQuery(selectSql);

    // Get the metadata of the ResultSet
    ResultSetMetaData rsmd = rs.getMetaData();

    // Get the number of columns in the ResultSet
    int columnCount = rsmd.getColumnCount();

    // Create a DefaultTableModel to hold the data for the JTable
    DefaultTableModel model = new DefaultTableModel();

    // Add the column names to the model
    for (int i = 1; i <= columnCount; i++) {
        model.addColumn(rsmd.getColumnName(i));
    }

    // Add the rows to the model
    while (rs.next()) {
        Object[] row = new Object[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            row[i - 1] = rs.getObject(i);
        }
        model.addRow(row);
    }

    // Set the model for the JTable
    table.setModel(model);

    // Close the ResultSet and Statement objects
    rs.close();
    stmt.close();
}


//delete query for doctor table
public static void deleteDoctorTableRow(JTable table) throws SQLException {
    // Get the selected row index
    int selectedRow = table.getSelectedRow();

    // Get the value of the 'id' column in the selected row
    String id = table.getValueAt(selectedRow, 0).toString();

    // Create a PreparedStatement object to execute parameterized SQL queries
    PreparedStatement pstmt = conn.prepareStatement("DELETE FROM doctortable WHERE id = ?");

    // Set the parameter value for the PreparedStatement
    pstmt.setString(1, id);

    // Execute the PreparedStatement
    int rowCount = pstmt.executeUpdate();
    System.out.println(rowCount + " row(s) deleted");

    // Close the PreparedStatement object
    pstmt.close();
}

//update function for doctor table
public static void updateDoctorTableRow(JTable table) throws SQLException {
    // Get the selected row index
    int selectedRow = table.getSelectedRow();

    // Get the values of the columns in the selected row
    String id = table.getValueAt(selectedRow, 0).toString();
    String name = table.getValueAt(selectedRow, 1).toString();
    String username = table.getValueAt(selectedRow, 2).toString();
    String cnic = table.getValueAt(selectedRow, 3).toString();
    String gender = table.getValueAt(selectedRow, 4).toString();
    String age = table.getValueAt(selectedRow, 5).toString();
    String password = table.getValueAt(selectedRow, 6).toString();
    String department = table.getValueAt(selectedRow, 7).toString();

    // Create a PreparedStatement object to execute parameterized SQL queries
    PreparedStatement pstmt = conn.prepareStatement("UPDATE doctortable SET name=?, username=?, cnic=?, gender=?, age=?, password=?, department=? WHERE id=?");

    // Set the parameter values for the PreparedStatement
    pstmt.setString(1, name);
    pstmt.setString(2, username);
    pstmt.setString(3, cnic);
    pstmt.setString(4, gender);
    pstmt.setString(5, age);
    pstmt.setString(6, password);
    pstmt.setString(7, department);
    pstmt.setString(8, id);

    // Execute the PreparedStatement
    int rowCount = pstmt.executeUpdate();
    System.out.println(rowCount + " row(s) updated");

    // Close the PreparedStatement object
    pstmt.close();
}




}
